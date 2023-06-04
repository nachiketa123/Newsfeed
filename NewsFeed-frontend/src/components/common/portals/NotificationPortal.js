import ReactDOM from 'react-dom';
import React from 'react';
import Notification from '../Notification';


const NotificationPortal = (props) => {
    return ( 
        <React.Fragment>
            {ReactDOM.createPortal(
                <Notification 
                    status={props.status} 
                    message={props.message}
                />,
                document.getElementById('notification-container')
            )}
        </React.Fragment>
     );
}
 
export default NotificationPortal;