import React, { Component } from 'react';
import './css/Ticker.css';
class Ticker extends Component {
    state = {
        sessionTrades: 'RECENT TRADES - ',
    };
    render() {
        const { tickerFeedMsg } = this.props;
        return (
            <header as='h2'>
                <div id='Ticker'>
                    <div id='Ticker-Item'>{tickerFeedMsg}</div>
                </div>
            </header>
        );
    }
}

export default Ticker;
