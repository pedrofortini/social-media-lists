import React, { Component } from 'react';
import { InputGroup, Form, Input, Button, ButtonGroup, Container } from 'reactstrap';
import _ from "lodash";
import ReactTable from "react-table";
import "react-table/react-table.css";
import AppNavbar from './AppNavbar';

class PostList extends Component {

    emptyItem = {
        data: [],
        pages: null,
        loading: true,
        lists: '',
        networks: '',
        text: '',
        userlogin: ''
    };

    constructor() {
        super();
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.fetchData = this.fetchData.bind(this);
    }

    handleChange(event) {

        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async requestData(page, pageSize, sorted, lists, networks, text, userlogin) {

        let filteredData = await ( await fetch('/social-media-lists-api/v1/posts?currentPage='
        + page + "&pageSize=" + pageSize, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }})).json;

        const sortedData = _.orderBy(
            filteredData,
            sorted.map(sort => {
                return row => {
                    if (row[sort.id] === null || row[sort.id] === undefined) {
                        return -Infinity;
                    }
                    return typeof row[sort.id] === "string"
                        ? row[sort.id].toLowerCase()
                    :    row[sort.id];
                };
            }),
            sorted.map(d => (d.desc ? "desc" : "asc"))
        );

        const res = {
            rows: sortedData.slice(pageSize * page, pageSize * page + pageSize),
            pages: Math.ceil(filteredData.length / pageSize)
        };

        this.setState({
            data: res.rows,
            pages: res.pages,
            loading: false});
    }

    async handleSubmit(event) {

        event.preventDefault();
        this.setState({ loading: true });
        const {item} = this.state;

        requestData(10, 0, null,
                    item.lists, item.networks,
                    item.text, item.userlogin);
    }

    async fetchData(state, instance) {

        this.setState({ loading: true });
        const {item} = this.state;

        requestData(state.pageSize, state.page,
                    state.sorted, item.lists,
                    item.networks, item.text, item.userlogin);
    }

    render() {

        const {item} = this.state;
        const title = <h2>Lists of Posts from Social Media</h2>;
        return (
            <div>
                <AppNavbar/>
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <InputGroup>
                            <Input type="text" name="list" id="lists" value={item.lists || ''}
                                   onChange={this.handleChange} autoComplete="lists" placeholder="List(s) that the Author belongs"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <InputGroup>
                            <Input type="text" name="networks" id="networks" value={item.networks || ''}
                                   onChange={this.handleChange} autoComplete="networks" placeholder="Social Network(s) of the posts"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <InputGroup>
                            <Input type="text" name="text" id="text" value={item.text || ''}
                                   onChange={this.handleChange} autoComplete="text" placeholder="Full text search on content"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <InputGroup>
                            <Input type="text" name="login" id="login" value={item.login || ''}
                                   onChange={this.handleChange} autoComplete="login" placeholder="Login of the Author"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <ButtonGroup>
                            <Button color="primary" type="submit">Search</Button>
                        </ButtonGroup>
                    </Form>
                    <br />
                    <ReactTable
                        columns={[
                            {
                                Header: "Author",
                                id: "author_name",
                                accessor: d => d.author_name
                            },
                            {
                                Header: "Created Date",
                                accessor: "created_date"
                            },
                            {
                                Header: "Social Network",
                                accessor: "social_network"
                            },
                            {
                                Header: "Link Original Post",
                                accessor: "link_original_post"
                            },
                            {
                                Header: "Post Content",
                                accessor: "post_content"
                            },
                            {
                                Header: "Lists Author Belongs",
                                accessor: "lists_belongs_to"
                            },
                        ]}
                        manual
                        data={item.data}
                        pages={item.pages}
                        loading={item.loading}
                        onFetchData={this.fetchData}
                        defaultPageSize={10}
                        className="-striped -highlight"
                />
                </Container>
            </div>
        );
    }
}

export default PostList;