import React, { Component } from 'react';
import FacebookIcon from '@material-ui/icons/Facebook';
import LinkedInIcon from '@material-ui/icons/LinkedIn';
import TwitterIcon from '@material-ui/icons/Twitter';
import InstagramIcon from '@material-ui/icons/Instagram';
import './css/Footer.css';
class Footer extends Component {
    state = {};
    render() {
        return (
            <React.Fragment>
                <div id='Footer'>
                    <div>The INVESTMENT GUILD. Not the worst thing to come out of 2020</div>
                    <div>
                        <a href='https://www.facebook.com/EuropeanInvestmentBank'>
                            <FacebookIcon id='Icon' style={{ 'font-size': '1.5vw', color: 'white', 'text-decoration': 'none' }} />
                        </a>
                        <a href='https://www.instagram.com/thisisbillgates/?hl=en'>
                            <InstagramIcon id='Icon' style={{ 'font-size': '1.5vw', color: 'white', 'text-decoration': 'none' }} />
                        </a>
                        <a href='https://www.theatlantic.com/letters/archive/2020/02/twitter-is-bad-for-the-news/605782/'>
                            <TwitterIcon id='Icon' style={{ 'font-size': '1.5vw', color: 'white', 'text-decoration': 'none' }} />
                        </a>
                        <a href='https://www.linkedin.com/in/ron-lobo/'>
                            <LinkedInIcon id='Icon' style={{ 'font-size': '1.5vw', color: 'white', 'text-decoration': 'none' }} />
                        </a>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default Footer;
