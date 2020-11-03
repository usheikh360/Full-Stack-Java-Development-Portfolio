import React from "react"
import { Form, Button, Row } from 'react-bootstrap'
class SelectedItemForm extends React.Component {

    render() {
        let { itemId, handleSubmit, message } = this.props;
        console.log(itemId)
        return (
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="messages">
                    <Form.Label className="menu-title">Messages</Form.Label>
                    <Form.Control type="text" name="message" readOnly value={message} />

                </Form.Group>
                <div className="item-id-div">
                    <Form.Group controlId="itemSelected">
                        <Form.Label className="menu-title-item" >Item</Form.Label>
                        <Form.Control type="text" name="selected" readOnly className="item-number-display" value={itemId !== -1 ? itemId : ""} />
                    </Form.Group>
                </div>

                <Button variant="primary" type="submit" value="dollar" className="make-puechase-button">
                    Make Purchase
                </Button>
            </Form>
        )
    }
}

export default SelectedItemForm