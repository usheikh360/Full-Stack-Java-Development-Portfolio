import React from "react"
import { Container, Row, Col, Button, Card, ListGroup } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';
import "../App.css"
class ItemCard extends React.Component {
    render() {
        return (
            <Col md={4}>
                <Card style={{ width: '18rem' }} className="card mb-3 item-card" >
                    <Card.Body>
                        <p className="item-id-on-card">{this.props.item.id}</p>
                        <Card.Title>{this.props.item.name}</Card.Title>
                        <Card.Text>
                            $ {this.props.item.price}
                        </Card.Text>
                        <Card.Text>
                            Quantity left: {this.props.item.quantity}
                        </Card.Text>
                    </Card.Body>
                    <Button onClick={this.props.itemSelected} value={this.props.item.id} >Select Item</Button>
                </Card>
            </Col >
        )
    }
}

export default ItemCard