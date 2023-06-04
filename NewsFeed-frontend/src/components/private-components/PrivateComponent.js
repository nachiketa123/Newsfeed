import React,{ useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import UserAuthContext from '../../application-context/UserAuthContext';


const PrivateComponent = ({Component}) => {
    const navigate = useNavigate();
    const { isAuthenticated } = useContext(UserAuthContext);

    useEffect(()=>{
        if(!isAuthenticated){
            navigate('/login')
        }
    },[isAuthenticated,navigate])

    return ( 
       <Component/>
    );
}
 
export default PrivateComponent;