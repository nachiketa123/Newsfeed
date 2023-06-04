import './App.css';
import Header from './components/header/header';
import React, { useEffect, useState } from 'react';
import UserAuthContext from './application-context/UserAuthContext';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Register from './components/auth/Register';
import Login from './components/auth/Login';
import PrivateComponent from './components/private-components/PrivateComponent';
import NewsFeed from './components/private-components/Newsfeed';
import { userActions } from './store/user-slice';
import { useDispatch } from 'react-redux';
import Footer from './components/footer/Footer.js';
import resetAllAction from './store/reset-all-action';
import { notificationActions } from './store/notification-slice';
import errorHandler from "./store/actions/notification-action";
import jwt_decode from 'jwt-decode';


function App() {

  const [isAuthenticated,setIsAuthenticated] = useState(false)

  const dispatch = useDispatch();

  useEffect(()=>{
    const token = localStorage.getItem('token')
    if(token && token.length!==0){
      setIsAuthenticated(token.length !== 0)

      const tokenData = jwt_decode(token)

      const user = {
        name: tokenData.name,
        email: tokenData.email,
        user_id: tokenData.id,
        token,
      }
      dispatch(userActions.setUser(user))
    }
  },[dispatch])

  const handleLogout = () =>{
    setIsAuthenticated(false)
    localStorage.removeItem('token')
    dispatch(resetAllAction());
  }

  const handleLogin = async (email,password) =>{

    //Notification loading
    dispatch(notificationActions.updatePendingStatus());

    const res = await fetch('/api/user/login',
        { method:'post',
            headers: {
                'Content-Type': 'application/json'
            }, 
            body:JSON.stringify({email: email,password: password})
        })
      

      //In case of errors
      if(!res.ok){
        const err = await res.json();
        dispatch(errorHandler(err.httpStatusCode,err.message))
        return;
      }
      const data = await res.json();
      const token = data.token;

      const authData = jwt_decode(token)

      const authStatus = Object.keys(authData).length !== 0;
      
      setIsAuthenticated(authStatus)
      
      dispatch(userActions.setUser({
        email: authData.email,
        name: authData.name,
        user_id: authData.id,
        token,
      }))

      localStorage.setItem('token',token)  
      
      //Notification success
      dispatch(notificationActions.updateSuccessStatus())
  }

  return (
    <BrowserRouter>
        <UserAuthContext.Provider value={{
          isAuthenticated,
          login: handleLogin,
          logout: handleLogout,
        }}>
          <div className="App">
            <header className="App-header">
              <Header/>
            </header>
            <section style={{marginBottom:"7em"}} className='container'>
              <Routes>
                <Route path='/login' element={<Login/>}/>
                <Route path='/register' element={<Register/>}/>
                <Route path='/' element={<PrivateComponent Component={NewsFeed}/>} />
              </Routes>
            </section>
            <Footer/>
          </div>
        </UserAuthContext.Provider>
    </BrowserRouter>
  );
}

export default App;
