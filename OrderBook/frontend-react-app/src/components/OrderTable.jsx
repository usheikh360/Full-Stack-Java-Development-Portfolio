import React from 'react';

import { TextField } from '@material-ui/core';

import { Select, MenuItem, Button } from '@material-ui/core';
import Moment from 'moment';
import './css/OrderTable.css';

class OrderTable extends React.Component {
    state = {
        query: '',
        columnToQuery: '',
    };

    static defaultProps = {
        orders: [
            {
                id: 1,
                mpidInfo: {
                    id: 0,
                    mpid: '',
                    name: '',
                },
                buyOrSell: 'BUY',
                stock: {
                    id: 1,
                    symbol: 'GOOG',
                    name: 'Google',
                },
                price: 91,
                size: 20,
                time: 0,
                complete: false,
            },
        ],
    };

    recordsAfterPagingAndSorting = () => {
        if (this.state.query && this.state.columnToQuery == 'Stock Id') {
            console.log('Stock id');
            return this.props.orders.filter(x => x.id == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Mpid') {
            console.log('Mpid');
            return this.props.orders.filter(x => x.mpidInfo.mpid.includes(this.state.query));
        } else if (this.state.query && this.state.columnToQuery == 'Symbol') {
            console.log('Symbol');
            return this.props.orders.filter(x => x.stock.symbol.includes(this.state.query));
        } else if (this.state.query && this.state.columnToQuery == 'Price') {
            console.log('Price');
            return this.props.orders.filter(x => x.price <= this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Quantity') {
            console.log('Size');
            return this.props.orders.filter(x => x.size <= this.state.query);
        }
        return this.props.orders;
    };

    handleInputChange = e => {
        const { name, value } = e.target;
        this.setState({
            ...this.state,
            [name]: value,
        });
    };

    render() {
        const { handleSelectOrder, buyorsell } = this.props;
        return (
            <div id='Complete-Table-Area'>
                <div>
                    <div class='buyOrSell'>{buyorsell}</div>
                </div>
                <div id='Search-Area'>
                    <TextField
                        className='textField'
                        label='Search'
                        InputLabelProps={{ style: { fontSize: '1vw', color: '#ffa41b', height: '1.1vw' } }}
                        inputProps={{ style: { fontSize: '1vw', color: '#ffa41b', height: '1.1vw' } }}
                        value={this.state.query}
                        onChange={e => this.setState({ query: e.target.value })}
                        floatingLabelFixed
                    />
                    <Select label='Columns' name='columnToQuery' value={this.state.columnToQuery} onChange={this.handleInputChange} variant='outlined' className='dropper'>
                        <MenuItem value=''>None</MenuItem>
                        {this.props.headingColumns.map(item => (
                            <MenuItem key={item} value={item}>
                                {item}
                            </MenuItem>
                        ))}
                    </Select>
                </div>
                <table className='fixed_header'>
                    <thead>
                        <tr id='headingR'>
                            {this.props.headingColumns.map((col, index) => (
                                <div>
                                    <th class='orderHeader' key={index}>
                                        {col}
                                    </th>
                                </div>
                            ))}
                        </tr>
                    </thead>
                    <tbody id='Table-Body'>
                        {this.recordsAfterPagingAndSorting().map((order, i) => {
                            return (
                                <tr key={i}>
                                    <Button
                                        className='Row-Button'
                                        onClick={() => {
                                            handleSelectOrder(order);
                                        }}
                                    >
                                        <td class='orderCell1'>{order.id}</td>
                                        <td class='orderCell1'>{order.mpidInfo.mpid}</td>
                                        <td class='orderCell1'>{order.stock.symbol}</td>
                                        <td class='orderCell1'>£{order.price}</td>
                                        <td class='orderCell1'>{order.size}</td>
                                        <td class='orderCell1'>{Moment(order.time).format('DD/MM/yyyy HH:mm:SS')}</td>
                                    </Button>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        );
    }
}
export default OrderTable;