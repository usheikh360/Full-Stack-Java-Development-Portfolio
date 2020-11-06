import React, { Component } from 'react';
import MainPage from './components/MainPage.jsx';
import LoginPage from './components/LoginPage.jsx';

class App extends Component {
    state = {
        isLoggedIn: false,
    };

    handleSuccessfulLogin = (username, id) => {
        this.setState({
            currentUser: { username: username, id: id },
            isLoggedIn: true,
        });
    };

    handleLogOut = () => {
        this.setState({ isLoggedIn: false });
    };

    render() {
        let whatToShow = <LoginPage onSuccessfulLogin={this.handleSuccessfulLogin} />;
        if (this.state.isLoggedIn === true) {
            whatToShow = <MainPage onLogOut={this.handleLogOut} currentUser={this.state.currentUser} />;
        } else {
            whatToShow = <LoginPage onSuccessfulLogin={this.handleSuccessfulLogin} next={this.next} />;
        }
        return <div className='App'>{whatToShow}</div>;
    }
}

export default App;
