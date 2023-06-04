import './Login.css';
import React,{ useRef, useContext, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import UserAuthContext from '../../application-context/UserAuthContext';

const Login = () => {

    //ref
    const loginForm = useRef();

    //navigation init
    const navigate = useNavigate();

    //context
    let { isAuthenticated, login } = useContext(UserAuthContext);

    //side effect
    useEffect(()=>{
        if(isAuthenticated){
            navigate('/')
        }
    },[isAuthenticated,navigate])


    //click handler
    const handleLogin = (e) =>{
        e.preventDefault();
        const {email, password} = loginForm.current;

        login(email.value, password.value)
        
    }

    return (

        <div className="login">
            <h2 className='text-center'>Login Form</h2>
            <form ref={loginForm} className='login-form' action="submit" onSubmit={handleLogin}>
                <input required={true} name='email' className="form-control m-2" type="email" placeholder="Email" aria-label="Email"/>
                <input required={true} name='password' className="form-control m-2" type="password" placeholder="Password" aria-label="Password"/>
                <button type='submit' className='btn btn-primary m-2'> Login </button>
                <Link to='/register' className='text-center link'>Do not have an account? Click to register.</Link>
            </form>
        </div>
        );
}
 
export default Login;