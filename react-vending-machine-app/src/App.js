import React from 'react';
import './App.css';
import { Container, Row, Col, Grid, Button, Card, ListGroup } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';
import ItemCard from "./components/ItemCard"
import CollectMoneyForm from "./components/CollectMoneyForm"
import SelectedItemForm from "./components/SelectedItemForm"
import ChangeForm from "./components/ChangeForm"
const SERVICE_URL = "http://tsg-vending.herokuapp.com"
class App extends React.Component {
  state = {
    loading: false,
    cardInfo: [{
      "id": 1,
      "name": "Fake",
      "price": 2.99,
      "quantity": 16
    }
    ],
    selectedItem: -1,
    coinsEntered: {
      "dollar": 0,
      "quarter": 0,
      "dime": 0,
      "nickle": 0
    },
    totalMoneyEntered: 0.00,
    change: {
      "quarters": 0,
      "dimes": 0,
      "nickels": 0,
      "pennies": 0
    },
    message: ""
  }

  componentDidMount() {
    console.log("App is now mounted.")
    this.loadItemData();
  }

  loadItemData() {
    this.setState({ loading: true })
    console.log("Loading items data")
    fetch(SERVICE_URL + "/items")
      .then(data => data.json())
      .then(data => this.setState(
        { cardInfo: data, loading: false }
      ))
  }

  selectedItem = (event) => {
    this.setState({ selectedItem: event.target.value })
  }


  addCoins = (event) => {
    if (event) event.preventDefault();
    if (event.target.value === "dollar") {
      console.log("dollar")
      this.setState(prevState => ({
        coinsEntered: { ...prevState.coinsEntered, dollar: prevState.coinsEntered.dollar + 1 },
        totalMoneyEntered: prevState.totalMoneyEntered + 1.0
      }));

    } else if (event.target.value === "quarter") {
      console.log("quarter")
      this.setState(prevState => ({
        coinsEntered: { ...prevState.coinsEntered, quarter: prevState.coinsEntered.quarter + 1 },
        totalMoneyEntered: prevState.totalMoneyEntered + 0.25
      }));

    }
    else if (event.target.value === "dime") {
      console.log("dime")
      this.setState(prevState => ({
        coinsEntered: { ...prevState.coinsEntered, dime: prevState.coinsEntered.dime + 1 },
        totalMoneyEntered: prevState.totalMoneyEntered + 0.10
      }));

    }
    else if (event.target.value === "nickle") {
      console.log("nickle")
      this.setState(prevState => ({
        coinsEntered: { ...prevState.coinsEntered, nickle: prevState.coinsEntered.nickle + 1 },
        totalMoneyEntered: prevState.totalMoneyEntered + 0.05
      }));
    }

    this.setState(prevState => ({
      totalMoneyEntered: parseFloat(prevState.totalMoneyEntered.toFixed(2))
    }))
    console.log(this.state.coinsEntered);
  }

  makePurchase = (event) => {
    if (event) event.preventDefault();
    fetch(SERVICE_URL + "/money/" + this.state.totalMoneyEntered + "/item/" + this.state.selectedItem, {
      method: 'POST'
    })
      .then(data => {
        if (data.status == 422) {

          data.json().then(t => {
            this.setState({ message: t.message })
            console.log(this.state.message);
            console.log("HEY")
          })
        }
        else {
          data.json().then(response => {
            console.log("RESPONSE FROM API")
            console.log(response)
            console.log("HI")
            console.log(response.quarters)
            console.log(response.dimes)
            console.log(response.nickels)
            console.log(response.pennies)
            this.setState({
              change: response,
              message: "Thank You!!!",
            })
            console.log("change:")
            console.log(this.state.change)
            this.loadItemData();
          })
        }
      })
  }

  changeReturn = (event) => {
    if (event) event.preventDefault();
    this.setState({
      totalMoneyEntered: 0.00,
      message: "",
      selectedItem: -1,
      change: {
        "quarters": 0,
        "dimes": 0,
        "nickles": 0,
        "pennies": 0
      }
    })
  }


  render() {
    const itemCardComponents = this.state.cardInfo.map((item, i) => <ItemCard key={i} item={item} itemSelected={this.selectedItem} />);

    return (<Container fluid>
      <Row>
        <Col>
          <h1 className="text-center">Vending Machine</h1>
        </Col>
      </Row>
      <hr />
      <Row>
        <Col lg={8}>
          <Row>
            {itemCardComponents}
          </Row>
        </Col>
        <Col lg={4}>
          <CollectMoneyForm handleCoinSelected={this.addCoins} totalMoneyEntered={this.state.totalMoneyEntered} />
          <hr></hr>
          <SelectedItemForm itemId={this.state.selectedItem} handleSubmit={this.makePurchase} message={this.state.message} />
          <hr></hr>
          <ChangeForm change={this.state.change} handleSubmit={this.changeReturn} />
        </Col>
      </Row>
    </Container>)
  }
}

export default App;