import React from 'react';

const UserAuthContext = React.createContext({
    isAuthenticated: false,
    login: (email, password)=>{},
    logout: ()=>{},
    token:'',
});

export default UserAuthContext;