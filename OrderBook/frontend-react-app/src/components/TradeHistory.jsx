import React, { Component } from 'react';
import { Card, CardContent } from '@material-ui/core';
import TradeTable from './TradeTable.jsx';
const SERVICE_URL = 'http://localhost:8080';

class TradeHistory extends Component {
    state = {
        allTrades: [
            {
                id: 0,
                userid: {
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

    loadItemData() {
        fetch(SERVICE_URL + '/getAllTrades')
            .then(data => data.json())
            .then(data => this.setState({ allTrades: data }));
    }

    componentDidMount() {
        this.props.handleTradeFeedback('Please use the search bar to view a specific historic trade');
        this.loadItemData();
    }

    render() {
        return (
            <div>
                <Card>
                    <CardContent className='transactionBacker'>
                        <TradeTable tradeData={this.state.allTrades} headingColumns={['Trade', 'Stock', 'Price', 'Quantity', 'User', 'Buyer', 'Seller', 'Time']} />
                    </CardContent>
                </Card>
            </div>
        );
    }
}

export default TradeHistory;
