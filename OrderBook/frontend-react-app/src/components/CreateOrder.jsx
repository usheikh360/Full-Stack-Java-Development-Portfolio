import React from 'react';
import TextField from '@material-ui/core/TextField';
import './css/CreateOrder.css';
import { FormControl, RadioGroup, FormControlLabel, Radio, Button } from '@material-ui/core';
import { InputLabel, Select, MenuItem } from '@material-ui/core';
import Moment from 'react-moment';
import { Report } from '@material-ui/icons';

const SERVICE_URL = 'http://localhost:8080';

const typeOfAction = [
    { id: 'BUY', title: 'BUY' },
    { id: 'SELL', title: 'SELL' },
];

class CreateOrder extends React.Component {
    constructor() {
        super();
        this.state = {
            allSymbols: [{ id: 0, symbol: '', name: '' }],
            allMpids: [{ id: 0, mpid: '', name: '' }],

            newOrder: {
                buyorsell: '',
                mpidInfo: {
                    id: '',
                    mpid: '',
                    name: '',
                },
                stock: {
                    id: '',
                    symbol: '',
                    name: '',
                },
                price: '',
                size: '',
                time: '',
            },
            errors: {},
        };
    }
    handleInputChange = e => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            newOrder: { ...prevState.newOrder, [name]: value },
        }));
    };

    handleSubmit = e => {
        const { handleTradeFeedback } = this.props;
        e.preventDefault();
        this.setState({ time: Moment.getDatetime });

        fetch(SERVICE_URL + '/addOrder/' + this.state.newOrder.buyorsell, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.state.newOrder),
        })
            .then(response => {
                console.log(response.status);
                return response.text();
            })
            .then(data => {
                this.setState({ newOrder: { buyorsell: '', stock: '', price: '', size: '', mpid: '' } });
                handleTradeFeedback(data);
                console.log(data);
                this.loadItemData();
            });
    };

    componentDidMount() {
        this.props.handleTradeFeedback('Please fill out all fields to create a new order');
        this.loadItemData();
    }

    loadItemData() {
        console.log('Loading items data');
        fetch(SERVICE_URL + '/getAllStocks')
            .then(data => data.json())
            .then(data => this.setState({ allSymbols: data }));
        fetch(SERVICE_URL + '/getMpid')
            .then(data => data.json())
            .then(data => this.setState({ allMpids: data }));
    }

    resetForm = () => {
        this.setState({
            newOrder: {
                buyorsell: '',
                mpidInfo: {
                    id: '',
                    mpid: '',
                    name: '',
                },
                stock: {
                    id: '',
                    symbol: '',
                    name: '',
                },
                price: '',
                size: '',
                time: '',
            },
        });
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit} autoComplete='off' id='myform' STYLE='color: wheat'>
                <FormControl>
                    <h1>CREATE ORDER</h1>
                    <RadioGroup row name='buyorsell' value={this.state.newOrder.buyorsell} onChange={this.handleInputChange}>
                        {typeOfAction.map(item => (
                            <FormControlLabel key={item.id} value={item.id} control={<Radio STYLE />} label={item.title} />
                        ))}
                    </RadioGroup>
                </FormControl>

                <div className='Symbol-Title'>Symbol</div>
                <FormControl variant='outlined'>
                    <Select
                        STYLE='background-color: wheat; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);  font-weight: bold;'
                        error={this.validate === true ? true : false}
                        label='Symbol'
                        name='stock'
                        value={this.state.newOrder.stock}
                        onChange={this.handleInputChange}
                    >
                        {this.state.allSymbols.map(item => (
                            <MenuItem key={item.id} value={item}>
                                {item.symbol}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <br></br>
                <TextField
                    STYLE='background-color: wheat; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);'
                    variant='outlined'
                    label='£ price'
                    name='price'
                    type='number'
                    value={this.state.newOrder.price === 0.0 ? '' : this.state.newOrder.price}
                    onChange={this.handleInputChange}
                />

                <br></br>
                <TextField
                    STYLE='background-color: wheat; color: #fff; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);'
                    variant='outlined'
                    label='Number of Shares'
                    name='size'
                    type='number'
                    value={this.state.newOrder.size === 0 ? '' : this.state.newOrder.size}
                    onChange={this.handleInputChange}
                />
                <br></br>
                <div className='Symbol-Title'>MPID</div>
                <FormControl variant='outlined'>
                    <Select
                        STYLE='background-color: wheat; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);  font-weight: bold;'
                        label='MPID'
                        name='mpidInfo'
                        value={this.state.newOrder.mpidInfo}
                        onChange={this.handleInputChange}
                    >
                        {this.state.allMpids.map(item => (
                            <MenuItem key={item.id} value={item}>
                                {item.mpid}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <div>
                    <Button type='submit' text='Create Order' variant='contained' size='large' color='primary'>
                        Create Order
                    </Button>
                    <Button type='reset' variant='contained' size='large' text='Reset' color='default' onClick={this.resetForm}>
                        Reset
                    </Button>
                </div>
            </form>
        );
    }
}

export default CreateOrder;