import React, { Component } from 'react';
import { Card, CardContent } from '@material-ui/core';
import './css/MainDisplayPanel.css';
import CreateOrder from './CreateOrder';
import OrderBook from './OrderBook';
import EditOrder from './EditOrder';
import TradeHistory from './TradeHistory';
class MainDisplayPanel extends Component {
    state = {};

    handleSelectBuyOrder = order => {
        this.setState({
            completeBuy: order,
        });
        this.fetchSpread(order);
    };

    fetchSpread = order => {
        fetch('http://localhost:8080/' + 'getSpread/' + order.stock.id).then(response => {
            if (response.status === 200) {
                console.log(response);
                response.json().then(r => this.props.handleSetSpreadMsg(order, r));
            }
        });
    };

    handleSelectSellOrder = order => {
        this.setState({
            completeSell: order,
        });
        this.fetchSpread(order);
    };

    resetSelectedBuySell = () => {
        this.setState({
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
        });
    };

    render() {
        const { handleUpdateTickerMsg, componentToRender, handleTradeFeedback, currentUser, handleEditOrder, completeOrder, handleSetSpreadMsg, handleInputChange, handleSubmit } = this.props;

        let whatToShow = <OrderBook />;

        if (componentToRender === 'CreateOrder') {
            whatToShow = <CreateOrder handleTradeFeedback={handleTradeFeedback} />;
        } else if (componentToRender === 'OrderBook') {
            whatToShow = (
                <OrderBook
                    handleTradeFeedback={handleTradeFeedback}
                    currentUser={currentUser}
                    handleEditOrder={handleEditOrder}
                    completeSell={this.state.completeSell}
                    completeBuy={this.state.completeBuy}
                    handleSelectBuyOrder={this.handleSelectBuyOrder}
                    handleSelectSellOrder={this.handleSelectSellOrder}
                    handleUpdateTickerMsg={handleUpdateTickerMsg}
                    handleSetSpreadMsg={handleSetSpreadMsg}
                    resetSelectedBuySell={this.resetSelectedBuySell}
                />
            );
        } else if (componentToRender === 'EditOrder') {
            whatToShow = <EditOrder handleTradeFeedback={handleTradeFeedback} completeOrder={completeOrder} handleInputChange={handleInputChange} handleSubmit={handleSubmit} />;
        } else {
            whatToShow = <TradeHistory currentUser={currentUser} handleTradeFeedback={handleTradeFeedback} />;
        }

        return (
            <React.Fragment>
                <Card id='MainDisplayPanel'>
                    <CardContent>{whatToShow}</CardContent>
                </Card>
            </React.Fragment>
        );
    }
}

export default MainDisplayPanel;
