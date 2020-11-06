import React, { Component } from 'react';
import { Card, CardContent } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import './css/Matcher.css';

class Matcher extends Component {
    state = {};
    render() {
        const { handleEditOrder, loadItemData, handleTrade, handleTradeFeedback, completeBuy, completeSell } = this.props;
        const SERVICE_URL = 'http://localhost:8080/';

        let handleDeleteOrder = order => {
            if (order == undefined) {
                handleTradeFeedback('Please select an order before attempting to delete.');
            } else {
                console.log(completeBuy);
                fetch(SERVICE_URL + 'deleteOrder', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(order),
                }).then(response => {
                    if (response.status === 200) {
                        loadItemData();
                        handleTradeFeedback('Delete Successful');
                    } else {
                        response.json().then(data => {
                            console.log('FAIL');
                            handleTradeFeedback(data.message);
                        });
                    }
                });
            }
        };

        return (
            <div id='Matcher-Card-Area'>
                <Card id='Matcher-Card'>
                    <CardContent>
                        <div id='Matcher-Container'>
                            <div className='Matcher-Text'>
                                <div className='Order-Header'>Buy</div>
                                <div className='Order-Details'>
                                    Symbol: {completeBuy.stock.symbol} <br />
                                    Quantity: {completeBuy.size} <br />
                                    Bid: £{completeBuy.price}
                                    <br />
                                    <EditIcon
                                        id='Edit-Icon'
                                        style={{ cursor: 'pointer', fontSize: '2vw' }}
                                        onClick={() => {
                                            console.log(completeBuy);
                                            if (completeBuy.id != 'N/A') {
                                                handleEditOrder(completeBuy, 'BUY');
                                            } else {
                                                handleTradeFeedback('Please select and order before attempting an update');
                                            }
                                        }}
                                    />
                                    <DeleteIcon
                                        style={{ cursor: 'pointer', fontSize: '2vw' }}
                                        onClick={() => {
                                            if (completeBuy.id != 'N/A') {
                                                handleDeleteOrder(completeBuy);
                                            } else {
                                                handleTradeFeedback('Please select and order before attempting a delete');
                                            }
                                        }}
                                    />
                                </div>
                            </div>
                            <div className='Matcher-Text'>
                                <div id='vl'></div>
                                <Button id='Trade-Button' variant='contained' onClick={handleTrade}>
                                    Trade
                                </Button>
                            </div>
                            <div className='Matcher-Text'>
                                <div className='Order-Header' id='Sell-Header'>
                                    Sell
                                </div>
                                <div id='Sell-Order-Details'>
                                    Symbol: {completeSell.stock.symbol} <br />
                                    Quantity: {completeSell.size} <br />
                                    Ask: £{completeSell.price}
                                    <br />
                                    <DeleteIcon
                                        style={{ cursor: 'pointer', fontSize: '2vw' }}
                                        onClick={() => {
                                            if (completeSell.id != 'N/A') {
                                                handleDeleteOrder(completeSell);
                                            } else {
                                                handleTradeFeedback('Please select and order before attempting a delete');
                                            }
                                        }}
                                    />
                                    <EditIcon
                                        style={{ cursor: 'pointer', fontSize: '2vw' }}
                                        onClick={() => {
                                            if (completeSell.id != 'N/A') {
                                                handleEditOrder(completeSell, 'SELL');
                                            } else {
                                                handleTradeFeedback('Please select and order before attempting an update');
                                            }
                                        }}
                                    />
                                </div>
                            </div>
                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }
}

export default Matcher;
