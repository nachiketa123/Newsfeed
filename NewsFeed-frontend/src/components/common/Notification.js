import React, { useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { notificationActions } from '../../store/notification-slice';

const Notification = () => {
  const notificationTimer = useRef();
  const { status, message } = useSelector(state=>state.notification);
  const dispatch = useDispatch();

  useEffect(()=>{
    if(status === "SUCCESS" || status === "FAILURE"){
        if(notificationTimer.current !== null)
          clearTimeout(notificationTimer.current)
        
        notificationTimer.current = setTimeout(()=>{
            dispatch(notificationActions.resetStatus());
        },3000)
    }

    return ()=>{
        clearTimeout(notificationTimer.current);
        notificationTimer.current = null;
    }
  
  },[status,message,dispatch])

  const getBackgroundColorBasedOnStatus= (st)=>{
    switch (st) {
        case 'PENDING':
          return 'bg-warning';
        case 'SUCCESS':
          return 'bg-success';
        case 'FAILURE':
          return 'bg-danger';
        default:
          return 'bg-secondary';
    } 
  }

  return (
    status && status.length !==0 && <div className={`toast show 
    align-items-center 
    text-white 
    position-fixed
    bottom-0
    end-0
    mb-2
    z-3
    ${getBackgroundColorBasedOnStatus(status)}`} 
    >
      <div className="toast-body">
        <strong>{status + " " + message}</strong>
      </div>
    </div>
  );
};

export default Notification;
