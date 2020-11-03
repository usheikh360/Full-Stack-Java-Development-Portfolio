import React from "react"
import "../App.css"
import { Form, Button } from 'react-bootstrap'
class CollectMoneyForm extends React.Component {
    render() {
        let { contactData, handleCoinSelected, handleChange, totalMoneyEntered } = this.props;
        return (
            <Form >
                <Form.Group controlId="totalMoneyIn">
                    <Form.Label className="menu-title" >Total $ In</Form.Label>
                    <Form.Control type="text" name="moneyIn" readOnly value={totalMoneyEntered.toFixed(2)} />
                </Form.Group>
                <Button variant="primary" type="submit" value="dollar" className="currency-buttons" onClick={handleCoinSelected}>
                    Add Dollar
                </Button>
                <Button variant="primary" type="submit" value="quarter" className="currency-buttons" onClick={handleCoinSelected}>
                    Add Quarter
                </Button>
                <br></br>
                <br></br>
                <Button variant="primary" type="submit" value="dime" className="currency-buttons" onClick={handleCoinSelected}>
                    Add Dime
                </Button>
                <Button variant="primary" type="submit" value="nickle" className="currency-buttons" onClick={handleCoinSelected}>
                    Add Nickle
                </Button>
            </Form>
        )
    }
}

export default CollectMoneyForm