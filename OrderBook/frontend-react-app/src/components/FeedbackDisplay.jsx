import React, { Component } from 'react';
import { Card, CardContent } from '@material-ui/core';
import './css/FeedbackDisplay.css';

class FeedbackDisplay extends Component {
    state = {};

    render() {
        const { feedbackMessage, spreadMsg } = this.props;
        return (
            <React.Fragment>
                <Card id='FeedbackPanel'>
                    <div id='FeedbackPanel-Area'>
                        <CardContent>
                            <div>
                                <Card id='Feedback-Message'>
                                    <CardContent>{feedbackMessage}</CardContent>
                                </Card>
                            </div>
                            <div>
                                <Card id='Spead-Message'>
                                    <CardContent>Spread - {spreadMsg}</CardContent>
                                </Card>
                            </div>
                        </CardContent>
                    </div>
                </Card>
            </React.Fragment>
        );
    }
}

export default FeedbackDisplay;
