import React, { Component } from 'react';
import { Button } from '@material-ui/core';
import { Card, CardContent } from '@material-ui/core';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import DropDownMenu from './DropDownMenu';
import Ticker from './Ticker';
import Clock from './Clock';
import './css/NavBar.css';

class NavBar extends Component {
    state = {};
    render() {
        const { tickerFeedMsg, currentUser, onShowOrderForm, onShowOrderBook, onShowTradeHistory, onLogOut, handleTradeFeedback } = this.props;
        return (
            <React.Fragment>
                <React.Fragment>
                    <div>
                        <header as='h1' className='TopPanel'>
                            <DropDownMenu
                                id='MenuButton'
                                className='Header-Button'
                                onShowOrderForm={onShowOrderForm}
                                onShowOrderBook={onShowOrderBook}
                                onShowTradeHistory={onShowTradeHistory}
                                onHandleFeedback={handleTradeFeedback}
                            />
                            <Card id='Current-Logged-In-User-Card'>
                                <CardContent>Welcome back, {currentUser.username}!</CardContent>
                            </Card>
                            <Card id='Clock'>
                                <CardContent>
                                    <Clock />
                                </CardContent>
                            </Card>
                            <Button id='Sign-Out-Button' endIcon={<ExitToAppIcon id='Exit-Icon' />} onClick={onLogOut} variant='outlined'>
                                Sign Out
                            </Button>
                        </header>
                    </div>
                    <Ticker tickerFeedMsg={tickerFeedMsg} />
                </React.Fragment>
            </React.Fragment>
        );
    }
}

export default NavBar;
