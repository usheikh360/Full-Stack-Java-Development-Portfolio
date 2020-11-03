import React, { Component } from 'react';
import { Button } from '@material-ui/core';
import logo from './images/LogoMakr_1eHB7J.png';
import TextField from '@material-ui/core/TextField';
import './css/LoginPage.css';

class LoginPage extends Component {
    state = {
        username: '',
        password: '',
        userId: '',
        createNewUser: false,
        reponseMsg: null,
    };

    render() {
        const SERVICE_URL = 'http://localhost:8080/';
        let { username, password } = this.state;

        let handleLoginAttempt = () => {
            if (username.length == 0 || password.length == 0) {
                this.setState({ reponseMsg: 'MISSING FIELDS' });
            }
            fetch(SERVICE_URL + 'login/' + username + '/' + password).then(response => {
                if (response.status === 200) {
                    response.json().then(data => this.props.onSuccessfulLogin(data.username, data.id));
                } else {
                    console.log(response);
                    response.json().then(data => this.setState({ reponseMsg: data.message }));
                }
            });
        };

        let handleCreateNewUser = () => {
            if (username.length == 0 || password.length == 0) {
                this.setState({ reponseMsg: 'MISSING FIELDS' });
            }
            fetch(SERVICE_URL + 'createnewuser/' + username + '/' + password, {
                method: 'POST',
            }).then(response => {
                if (response.status === 200) {
                    this.setState({ createNewUser: false });
                    this.setState({ reponseMsg: 'NEW USER CREATED' });
                } else {
                    response.json().then(data => {
                        this.setState({ reponseMsg: data.message });
                    });
                }
            });
        };

        let handleDisplayNewUserButton = () => {
            this.setState({ createNewUser: true });
        };
        let handleDisplayLoginButton = () => {
            this.setState({ createNewUser: false });
        };

        let handleInputChange = e => {
            const { name, value } = e.target;
            this.setState({
                ...this.state,
                [name]: value,
            });
        };

        let whichMsg = (
            <Button id='New-User-Button' onClick={handleDisplayNewUserButton}>
                New To the INVESTMENT GUILD? Click here to start making bank
            </Button>
        );

        let whichButton = (
            <Button onClick={handleLoginAttempt} variant='contained' id='Login-Button' value={this.state.inputPassword} onChange={this.setState.inputPassword}>
                Log in
            </Button>
        );

        if (this.state.createNewUser === false) {
            whichButton = (
                <Button onClick={handleLoginAttempt} variant='contained' id='Login-Button' value={this.state.inputPassword} onChange={this.setState.inputPassword} style={{ width: '25vw' }}>
                    Log in
                </Button>
            );
            whichMsg = (
                <Button id='New-User-Button' onClick={handleDisplayNewUserButton}>
                    New To the INVESTMENT GUILD? Click here to start making bank
                </Button>
            );
        } else {
            whichButton = (
                <Button onClick={handleCreateNewUser} variant='contained' id='Login-Button' value={this.state.inputPassword} onChange={this.setState.inputPassword} style={{ width: '25vw' }}>
                    Create New User
                </Button>
            );
            whichMsg = (
                <Button id='New-User-Button' onClick={handleDisplayLoginButton}>
                    Current User? Click here to log in
                </Button>
            );
        }

        let ResponseMessageClass = 'Response-Hide';

        if (this.state.reponseMsg != null) {
            ResponseMessageClass = 'Response-Show';
        }

        return (
            <React.Fragment>
                <div id='Main-Body'>
                    <img id='Logo' src={logo} alt='Logo' />
                    <div id='Sign-In-Header'>Sign In. </div>
                    <div>
                        <div id='Form-And-New-User-Button'>
                            <form className='Login-Form Text' noValidate autoComplete='off'>
                                <TextField
                                    InputLabelProps={{ style: { fontSize: '1.5vw' } }}
                                    inputProps={{ style: { fontSize: '2vw' } }}
                                    fullWidth
                                    className='Input-Field'
                                    id='Username'
                                    label='Username'
                                    name='username'
                                    onChange={handleInputChange}
                                />
                                <br />
                                <TextField
                                    InputLabelProps={{ style: { fontSize: '1.5vw' } }}
                                    inputProps={{ style: { fontSize: '2vw' } }}
                                    fullWidth
                                    type='password'
                                    className='Input-Field'
                                    label='Password'
                                    name='password'
                                    onChange={handleInputChange}
                                />
                                {whichButton}
                                <div className={ResponseMessageClass}>{this.state.reponseMsg}</div>
                            </form>
                            <div>{whichMsg}</div>
                        </div>
                    </div>
                    <div id='Copyright-Footer'>
                        <p>
                            INVESTMENTGUILD is optimized for learning, testing, and training. Examples might be simplified to improve reading and basic understanding. Tutorials, references, and
                            examples are constantly reviewed to avoid errors, but we cannot warrant full correctness of all content. While using this site, you agree to have read and accepted our
                            terms of use, cookie and privacy policy. Copyright 1999-2020 by INVEST Data. All Rights Reserved.
                        </p>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default LoginPage;
