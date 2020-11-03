import React from 'react';
import { TextField } from '@material-ui/core';
import { Select, MenuItem } from '@material-ui/core';
import Moment from 'moment';
import './css/OrderTable.css';

class TradeTable extends React.Component {
    state = {
        query: '',
        columnToQuery: '',
    };

    static defaultProps = {
        tradeData: [
            {
                id: 0,
                userInfo: {
                    id: 0,
                    username: '',
                    password: '',
                },
                buyOrder: {
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
                sellOrder: {
                    id: 1,
                    mpidInfo: {
                        id: 0,
                        mpid: '',
                        name: '',
                    },
                    buyOrSell: 'SELL',
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
                time: 0,
            },
        ],
    };

    recordsAfterPagingAndSorting = () => {
        if (this.state.query && this.state.columnToQuery == 'Trade') {
            return this.props.tradeData.filter(x => x.id == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Stock') {
            return this.props.tradeData.filter(x => x.sellOrder.stock.symbol == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Shares') {
            return this.props.tradeData.filter(x => x.buyOrder.size <= this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Price') {
            return this.props.tradeData.filter(x => x.buyOrder.price <= this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'User') {
            return this.props.tradeData.filter(x => x.userid.username == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Buyer') {
            return this.props.tradeData.filter(x => x.buyOrder.mpidInfo.mpid == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Seller') {
            return this.props.tradeData.filter(x => x.sellOrder.mpidInfo.mpid == this.state.query);
        } else if (this.state.query && this.state.columnToQuery == 'Time') {
            return this.props.tradeData.filter(x => x.time == this.state.query);
        }
        return this.props.tradeData;
    };

    handleInputChange = e => {
        const { name, value } = e.target;
        this.setState({
            ...this.state,
            [name]: value,
        });
    };

    render() {
        return (
            <div id='Complete-Table-Area'>
                <div>
                    <div id='Trade-History-Header'>TRADE HISTORY</div>
                </div>
                <div id='Search-Area'>
                    <TextField
                        InputLabelProps={{ style: { fontSize: '1vw', color: '#ffa41b', height: '1.1vw' } }}
                        inputProps={{ style: { fontSize: '1vw', color: '#ffa41b', height: '1.1vw' } }}
                        className='textField'
                        hintText='Search'
                        label='Search'
                        value={this.state.query}
                        onChange={e => this.setState({ query: e.target.value })}
                        floatingLabelFixed
                    />
                    <Select variant='outlined' className='dropper' label='Columns' name='columnToQuery' value={this.state.columnToQuery} onChange={this.handleInputChange}>
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
                        <tr id='headingR_'>
                            {this.props.headingColumns.map((col, index) => (
                                <div>
                                    <th class='orderHeader' key={index}>
                                        {col}
                                    </th>
                                </div>
                            ))}
                        </tr>
                    </thead>
                    <tbody class='transactionTable'>
                        {this.recordsAfterPagingAndSorting().map((trade, i) => {
                            return (
                                <tr key={i}>
                                    <td class='orderCell'>{trade.id}</td>
                                    <td class='orderCell'>{trade.sellOrder.stock.symbol}</td>
                                    <td class='orderCell'>£{trade.sellOrder.price}</td>
                                    <td class='orderCell'>{trade.size}</td>
                                    <td class='orderCell'>{trade.userid.username}</td>
                                    <td class='orderCell'>{trade.buyOrder.mpidInfo.mpid}</td>
                                    <td class='orderCell1'>{trade.sellOrder.mpidInfo.mpid}</td>
                                    <td class='orderCell'>{Moment(trade.time).format('DD/MM/yyyy HH:mm:SS')}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        );
    }
}
export default TradeTable;
