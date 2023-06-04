import { createAction } from "@reduxjs/toolkit"

// In order to reset whole app state, creating an action that can be used by all reducers
export default createAction('RESET_ALL')