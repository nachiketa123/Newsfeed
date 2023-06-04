import './Footer.css';
import NotificationPortal from '../common/portals/NotificationPortal';

import React from 'react';
const Footer = () => {
    return ( 
        <footer className="footer">
            <div className="container">
                <div className="footer-content">
                <span className="footer-text">NewsFeed</span>
                <span className="footer-separator">&copy;</span>
                <span className="footer-text">2023</span>
                </div>
            </div>

            {/* Notification */}
            <NotificationPortal status={"Pending"}/>
        </footer>

     );
}
 
export default Footer;