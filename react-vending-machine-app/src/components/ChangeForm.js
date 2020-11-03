import React from "react"
import { Form, Button } from 'react-bootstrap'
class ChangeForm extends React.Component {
    render() {
        let { handleSubmit, change } = this.props;
        let changeMessage = ""
        if (change.quarters > 0) {
            changeMessage += change.quarters + " Quarters ";
        }
        if (change.dimes > 0) {
            changeMessage += change.dimes + " Dimes ";
        }
        if (change.nickels > 0) {
            changeMessage += change.nickels + " Nickles ";
        }
        if (change.pennies > 0) {
            changeMessage += change.pennies + " Pennies ";
        }
        return (
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="changeFromForm">
                    <Form.Label className="menu-title">Change</Form.Label>
                    <Form.Control type="text" name="change" readOnly value={changeMessage} />
                </Form.Group>
                <Button variant="primary" type="submit" value="nickle" className="change-return-button">
                    Change Return
                </Button>
            </Form>
        )
    }
}

export default ChangeForm