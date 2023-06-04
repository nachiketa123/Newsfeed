import './Register.css';
import React,{ useRef, useState} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { notificationActions } from '../../store/notification-slice';
import errorHandler from '../../store/actions/notification-action';
import { useDispatch } from 'react-redux';

const Register = () => {

    //navigation
    const navigate = useNavigate();

    //ref
    const registrationForm = useRef();

    //state
    const [matchPassword, setMatchPassword] = useState(true);

    //dispatch
    const dispatch = useDispatch();

    //click handlers
    const handleFormSubmit = async (e) =>{
        e.preventDefault();

        const {name, email, password, cnfm_password}  = registrationForm.current;
        
        if(password.value !== cnfm_password.value){
            setMatchPassword(false);
            return;
        }
        
        setMatchPassword(true);
        
        //Setting notification to pending/loading status
        dispatch(notificationActions.updatePendingStatus());

        const res = await fetch('/api/user/register',
        { method:'post',
            headers: {
                'Content-Type': 'application/json'
            }, 
            body:JSON.stringify({name: name.value, email: email.value,password: password.value})
        })
        const authStatus = await res.json();
        if(!res.ok){
            dispatch(errorHandler(authStatus.httpStatusCode,authStatus.message))
            return;
        }

        if(authStatus){
        dispatch(notificationActions.updateSuccessStatus());
        navigate('/login')
        }else{
        alert('something went wrong')
        }


        
    }

    return (

        <div className="register">
            <h2 className='text-center'>Registration Form</h2>
            <form ref={registrationForm} className='register-form' action="submit" onSubmit={handleFormSubmit}>
                <input required={true} className="form-control m-2" name='name' type="text" placeholder="Name" aria-label="Name"/>
                <input required={true} className="form-control m-2" name= 'email' type="email" placeholder="Email" aria-label="Email"/>
                <input required={true} className="form-control m-2" name= 'password' type="password" placeholder="Password" aria-label="Password"/>
                <input required={true} className="form-control m-2" name= 'cnfm_password' type="password" placeholder="Confirm Password" aria-label="Confirm Password"/>
                {!matchPassword && <div className="alert alert-danger text-center">Password are not matching</div>}
                <button type='submit' className='btn btn-primary m-2'> Submit </button>
                <Link to='/login' className='text-center link'>Already have an account? Click to login.</Link>
            </form>
        </div>
     );
}
 
export default Register;