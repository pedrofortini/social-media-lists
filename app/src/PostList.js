import React, { Component } from 'react';
import { InputGroup, Form, FormGroup, Input, Button, ButtonGroup, Container } from 'reactstrap';
import _ from "lodash";
import ReactTable from "react-table";
import DatePicker from 'react-datepicker';
import { format } from "date-fns";

import 'react-datepicker/dist/react-datepicker.css';
import 'bootstrap/dist/css/bootstrap.min.css';
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
        userlogin: '',
        startDate: new Date(),
        endDate: new Date()
    };

    constructor() {
        super();
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.fetchData = this.fetchData.bind(this);
        this.handleStartDateChange = this.handleStartDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
    }

    handleStartDateChange(date){

        let item = {...this.state.item};
        item['startDate'] = date;
        this.setState({item});
    }

    handleEndDateChange(date){

        let item = {...this.state.item};
        item['endDate'] = date;
        this.setState({item});
    }

    handleChange(event) {

        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async fetchData(state, instance) {

        const {item} = this.state;

        if (item.startDate.getTime() > item.endDate.getTime()){
            alert("ERROR: Invalid Date Range! Please provide a valid one.");
            return;
        }

        const loadingItem =  {
            data: item.data,
            pages: item.pages,
            loading: true,
            lists: item.lists,
            networks: item.networks,
            text: item.text,
            userlogin: item.userlogin,
            startDate: item.startDate,
            endDate: item.endDate
        };

        this.setState(loadingItem);

        let page = state.page;
        let pageSize = state.pageSize;
        let sorted = state.sorted;
        let startDate = format(item.startDate, "dd/MM/yyyy");
        let endDate = format(item.endDate, "dd/MM/yyyy");
        console.log(startDate);
        console.log(endDate);

        if(page === undefined && pageSize === undefined && sorted === undefined){

            page = 0;
            pageSize = 10;
            sorted = [];
        }

        const posts = await (await fetch('/social-media-lists-api/v1/posts?currentPage='
                + page + "&pageSize=" + pageSize, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }})).json();

        const sortedData = _.orderBy(
            posts,
            sorted.map(sort => {
                return row => {
                    if (row[sort.id] === null || row[sort.id] === undefined) {
                        return -Infinity;
                    }
                    return typeof row[sort.id] === "string"
                        ? row[sort.id].toLowerCase()
                        : row[sort.id];
                };
            }),
            sorted.map(d => (d.desc ? "desc" : "asc"))
        );

        console.log(sortedData)
        const dataItem = {
                        data: sortedData.slice(pageSize * page, pageSize * page + pageSize),
                        pages: Math.ceil(sortedData.length / pageSize),
                        loading: false,
                        lists: item.lists,
                        networks: item.networks,
                        text: item.text,
                        userlogin: item.userlogin,
                        startDate: item.startDate,
                        endDate: item.endDate
                    };
        console.log(dataItem);
        this.setState({item: dataItem});
    }

    render() {

        const {item} = this.state;
        const title = <h2>Lists of Posts From Social Media Networks</h2>;

        return (
            <div>
                <AppNavbar/>
                <Container>
                    <Form onSubmit={this.fetchData}>
                        <InputGroup>
                            <Input type="text" name="lists" id="lists" value={item.lists || ''}
                                   onChange={this.handleChange} autoComplete="lists" placeholder="List(s) that the Author belongs"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <br />
                        <InputGroup>
                            <Input type="text" name="networks" id="networks" value={item.networks || ''}
                                   onChange={this.handleChange} autoComplete="networks" placeholder="Social Network(s) of the posts"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <br />
                        <InputGroup>
                            <Input type="text" name="text" id="text" value={item.text || ''}
                                   onChange={this.handleChange} autoComplete="text" placeholder="Full text search on content"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <br />
                        <InputGroup>
                            <Input type="text" name="userlogin" id="userlogin" value={item.userlogin || ''}
                                   onChange={this.handleChange} autoComplete="userlogin" placeholder="Login of the Author"
                                   style={{width: "370px"}} />
                        </InputGroup>
                        <br />
                        <FormGroup>
                            <label>Created Date: From </label>{' '}
                            <DatePicker
                                selected={ item.startDate }
                                onChange={ this.handleStartDateChange }
                                name="startDate"
                                dateFormat="dd/MM/yyyy"
                            />{' '}
                            <label>To </label>{' '}
                            <DatePicker
                                selected={ item.endDate }
                                onChange={ this.handleEndDateChange }
                                name="endDate"
                                dateFormat="dd/MM/yyyy"
                            />
                        </FormGroup>
                        <br />
                        <ButtonGroup>
                            <Button color="primary" onClick={this.fetchData} render="table">Search</Button>
                        </ButtonGroup>
                    </Form>
                    <br />
                    {title}
                    <ReactTable
                        id="table"
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