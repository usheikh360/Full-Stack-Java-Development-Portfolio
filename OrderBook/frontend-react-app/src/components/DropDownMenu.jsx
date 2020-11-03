import React from 'react';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import MenuIcon from '@material-ui/icons/Menu';

import './css/DropDownMenu.css';

export default function DropDownMenu(props) {
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = event => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl();
    };

    const handleCreateOrderClick = () => {
        props.onShowOrderForm();
        setAnchorEl();
    };

    const handleOrderBookClick = () => {
        props.onShowOrderBook();
        setAnchorEl();
    };

    const handleTradeHistoryClick = () => {
        props.onShowTradeHistory();
        setAnchorEl();
    };

    return (
        <div id='Whole-Menu'>
            <Button id='Open-Menu-Button' aria-controls='simple-menu' aria-haspopup='true' onClick={handleClick} startIcon={<MenuIcon id='Menu-Icon' />}>
                Open Menu
            </Button>
            <Menu id='simple-menu' anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleClose}>
                <MenuItem id='Menu-Item1' onClick={handleOrderBookClick}>
                    Order Book
                </MenuItem>
                <MenuItem id='Menu-Item2' onClick={handleCreateOrderClick}>
                    Create Order
                </MenuItem>
                <MenuItem id='Menu-Item2' onClick={handleTradeHistoryClick}>
                    Trade History
                </MenuItem>
            </Menu>
        </div>
    );
}
