import React from 'react';
import TextField from '@material-ui/core/TextField';
import { FormControl, RadioGroup, FormControlLabel, Radio, Button } from '@material-ui/core';
import { InputLabel, Select, MenuItem } from '@material-ui/core';
const SERVICE_URL = 'http://localhost:8080';

const typeOfAction = [
    { id: 'BUY', title: 'BUY' },
    { id: 'SELL', title: 'SELL' },
];

class EditOrder extends React.Component {
    constructor() {
        super();
        this.state = {
            allSymbols: [{ id: 0, symbol: '', name: '' }],
            allMpids: [{ id: 0, mpid: '', name: '' }],
            errors: {},
        };
    }

    componentDidMount() {
        this.props.handleTradeFeedback('Please fill out all fields to update an order ' + '\nSymbol: ' + this.props.completeOrder.stock.symbol + '\nMPID: ' + this.props.completeOrder.mpidInfo.mpid);
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

    render() {
        const { completeOrder, handleInputChange, handleSubmit } = this.props;
        return (
            <form onSubmit={handleSubmit} autoComplete='off' id='myform' STYLE='color: wheat'>
                <FormControl>
                    <h1>UPDATE ORDER</h1>
                    <RadioGroup row name='buyorsell' value={completeOrder.buyOrsell} onChange={handleInputChange}>
                        {typeOfAction.map(item => (
                            <FormControlLabel key={item.id} value={item.id} control={<Radio STYLE />} label={item.title} />
                        ))}
                    </RadioGroup>
                </FormControl>
                <div className='Symbol-Title'>Symbol</div>
                <FormControl variant='outlined'>
                    <Select
                        STYLE='background-color: wheat; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);  font-weight: bold;'
                        label='Symbol'
                        name='stock'
                        value={completeOrder.stock}
                        onChange={handleInputChange}
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
                    label='£price'
                    name='price'
                    value={completeOrder.price === 0.0 ? '' : completeOrder.price}
                    onChange={handleInputChange}
                />
                <br></br>
                <TextField
                    STYLE='background-color: wheat; color: #fff; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);'
                    variant='outlined'
                    label='Number of Shares'
                    name='size'
                    value={completeOrder.size === 0 ? '' : completeOrder.size}
                    onChange={handleInputChange}
                />
                <br></br>
                <div className='Symbol-Title'>MPID</div>

                <FormControl variant='outlined'>
                    <Select
                        STYLE='background-color: wheat; text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);  font-weight: bold;'
                        label='MPID'
                        name='mpidInfo'
                        value={completeOrder.mpidInfo}
                        onChange={handleInputChange}
                    >
                        {this.state.allMpids.map(item => (
                            <MenuItem key={item.id} value={item}>
                                {item.mpid}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <div>
                    <Button type='submit' text='Update Order' variant='contained' size='large' color='primary'>
                        Update Order
                    </Button>
                    <Button type='submit' variant='contained' size='large' text='Reset' color='default' onClick={this.resetForm}>
                        Reset
                    </Button>
                </div>
            </form>
        );
    }
}

export default EditOrder;
