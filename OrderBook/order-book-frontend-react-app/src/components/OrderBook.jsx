import React, { Component } from 'react';
import { Grid } from '@material-ui/core';
import Matcher from './Matcher';
import './css/OrderBook.css';
import OrderTable from './OrderTable';
const SERVICE_URL = 'http://localhost:8080';
class OrderBook extends Component {
    state = {};

    static defaultProps = {
        completeBuy: {
            id: 'N/A',
            mpidInfo: {
                id: '',
                mpid: '',
                name: '',
            },
            buyOrSell: 'BUY',
            stock: {
                id: '',
                symbol: '',
                name: '',
            },
            price: '',
            size: '',
            time: '',
            complete: '',
        },
        completeSell: {
            id: 'N/A',
            mpidInfo: {
                id: '',
                mpid: '',
                name: '',
            },
            buyOrSell: '',
            stock: {
                id: '',
                symbol: '',
                name: '',
            },
            price: '',
            size: '',
            time: '',
            complete: '',
        },
    };

    loadItemData = () => {
        fetch(SERVICE_URL + '/buyAll')
            .then(data => data.json())
            .then(data => this.setState({ buyAll: data, loading: false }));

        fetch(SERVICE_URL + '/sellAll')
            .then(data => data.json())
            .then(data => this.setState({ sellAll: data, loading: false }));
    };

    componentDidMount() {
        this.props.handleTradeFeedback('Please select a pair of orders to commence a trade');
        this.loadItemData();
    }

    render() {
        const { handleUpdateTickerMsg, handleTradeFeedback, currentUser, handleEditOrder, completeBuy, completeSell, handleSelectBuyOrder, handleSelectSellOrder, resetSelectedBuySell } = this.props;
        const SERVICE_URL = 'http://localhost:8080/';

        let handleTrade = () => {
            if (completeBuy.id == 'N/A' || completeSell.id == 'N/A') {
                handleTradeFeedback('Please select a pair of orders before attempting a trade.');
            } else {
                fetch(SERVICE_URL + 'addTrade/' + completeBuy.id + '/' + completeSell.id + '/' + currentUser.id, {
                    method: 'POST',
                }).then(response => {
                    if (response.status === 200) {
                        handleTradeFeedback('Trade Successful');
                        let sizeSold = Math.min(...[completeBuy.size, completeSell.size]);
                        handleUpdateTickerMsg(sizeSold + ' ' + completeBuy.stock.symbol + ' SOLD, ' + completeBuy.mpidInfo.name + ' - ');
                        this.loadItemData();
                        resetSelectedBuySell();
                    } else {
                        response.json().then(data => {
                            handleTradeFeedback(data.message);
                        });
                    }
                });
            }
        };
        return (
            <div id='Tables-And-Matcher-Area'>
                <div>
                    <div id='Table-Area'>
                        <Grid id='orderTable' container spacing={0}>
                            <div>
                                <Grid item xs={6} class='ordersTable' spacing={2}>
                                    <OrderTable
                                        orders={this.state.buyAll}
                                        buyorsell={'BID'}
                                        headingColumns={['Stock Id', 'Mpid', 'Symbol', 'Price', 'Quantity', 'Time']}
                                        handleSelectOrder={handleSelectBuyOrder}
                                    />
                                </Grid>
                            </div>
                            <div>
                                <Grid item xs={6} class='ordersTable' spacing={2}>
                                    <OrderTable
                                        orders={this.state.sellAll}
                                        buyorsell={'ASK'}
                                        headingColumns={['Stock Id', 'Mpid', 'Symbol', 'Price', 'Quantity', 'Time']}
                                        handleSelectOrder={handleSelectSellOrder}
                                    />
                                </Grid>
                            </div>
                        </Grid>
                    </div>
                </div>
                <div>
                    <Matcher
                        handleTradeFeedback={handleTradeFeedback}
                        handleTrade={handleTrade}
                        completeBuy={completeBuy}
                        completeSell={completeSell}
                        loadItemData={this.loadItemData}
                        handleEditOrder={handleEditOrder}
                    />
                </div>
            </div>
        );
    }
}

export default OrderBook;
