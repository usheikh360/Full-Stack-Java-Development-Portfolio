import React, { Component } from 'react';
import NavBar from './NavBar';
import MainDisplayPanel from './MainDisplayPanel';
import FeedbackDisplay from './FeedbackDisplay';
import { Grid } from '@material-ui/core';
import logo from './images/LogoMakr_1eHB7J.png';

import Footer from './Footer';
import './css/App.css';

import Moment from 'react-moment';
const SERVICE_URL = 'http://localhost:8080';

class MainPage extends Component {
    state = {
        componentToDisplay: 'OrderBook',
        feedbackMessage: 'Please select a pair of orders to commence a trade',
        spreadMsg: 'Awaiting Order',
        tickerFeedMsg: 'Recent Trades - ',

        completeOrder: {
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

    handleTradeFeedback = msg => {
        this.setState({
            feedbackMessage: msg,
        });
    };

    handleUpdateTickerMsg = msg => {
        let newMsg = this.state.tickerFeedMsg + msg;
        this.setState({
            tickerFeedMsg: newMsg,
        });
    };

    handleShowOrderForm = () => {
        this.setState({
            componentToDisplay: 'CreateOrder',
        });
    };

    handleShowOrderBook = () => {
        this.setState({
            componentToDisplay: 'OrderBook',
        });
    };

    handleShowTradeHistory = () => {
        this.setState({
            componentToDisplay: 'TradeHistory',
        });
    };

    handleEditOrder = (order, bos) => {
        console.log(order);
        this.setState({
            completeOrder: order,
        });
        console.log(order);
        this.setState({
            componentToDisplay: 'EditOrder',
        });
        console.log(order);
    };

    handleInputChange = e => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            completeOrder: { ...prevState.completeOrder, [name]: value },
        }));
    };

    handleSubmit = e => {
        e.preventDefault();
        console.log(this.state.completeOrder);
        fetch(SERVICE_URL + '/addOrder/' + this.state.completeOrder.buyorsell, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.state.completeOrder),
        })
            .then(response => {
                return response.text();
            })
            .then(data => {
                this.setState({ completeOrder: { buyorsell: '', stock: '', price: '', size: '', mpid: '' } });
                this.handleTradeFeedback(data);
                console.log(data);
            });
    };

    handleSetSpreadMsg = (order, spread) => {
        this.setState({
            spreadMsg: order.stock.symbol + ' : £' + spread,
        });
    };

    render() {
        const { onLogOut, currentUser } = this.props;
        return (
            <div className='App'>
                <NavBar
                    onShowOrderForm={this.handleShowOrderForm}
                    onShowOrderBook={this.handleShowOrderBook}
                    onShowTradeHistory={this.handleShowTradeHistory}
                    onLogOut={onLogOut}
                    currentUser={currentUser}
                    tickerFeedMsg={this.state.tickerFeedMsg}
                    onHandleFeedback={this.handleTradeFeedback}
                />
                <div id='Side-By-Side-Panels'>
                    <div>
                        <Grid id='Display-Grid' container spacing={10}>
                            <Grid item xs xs={4}>
                                <div>
                                    <img id='Logo1' src={logo} alt='Logo' />
                                </div>
                                <div>
                                    <FeedbackDisplay feedbackMessage={this.state.feedbackMessage} spreadMsg={this.state.spreadMsg} />
                                </div>
                            </Grid>
                            <Grid item xs={8}>
                                <MainDisplayPanel
                                    handleEditOrder={this.handleEditOrder}
                                    componentToRender={this.state.componentToDisplay}
                                    handleTradeFeedback={this.handleTradeFeedback}
                                    handleUpdateTickerMsg={this.handleUpdateTickerMsg}
                                    currentUser={currentUser}
                                    completeOrder={this.state.completeOrder} //ORDER TO EDIT
                                    handleInputChange={this.handleInputChange}
                                    handleSubmit={this.handleSubmit}
                                    handleSetSpreadMsg={this.handleSetSpreadMsg}
                                />
                            </Grid>
                        </Grid>
                    </div>
                </div>
                <Footer />
            </div>
        );
    }
}

export default MainPage;
