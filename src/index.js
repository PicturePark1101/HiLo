import React from 'react';
import ReactDOM from 'react-dom/client';

import Greeting from './GreetingApp';
import ArticleApp from './ArticleApp';
import Top5CDApp from './Top5CDApp';

import './index.css';

import reportWebVitals from './reportWebVitals';
/*
const router2 = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Chap3App />}>
      <Route path="/home" element={<Home />} />
      <Route path="/topics" element={<Topics />} />
      <Route path="/contact" element={<Contact />} />
    </Route>
  )
);
*/

const container = document.getElementById('root');
const root = ReactDOM.createRoot(container);
root.render(
  <React.StrictMode>
  { 
    <Greeting name="Jain" />
    // <ArticleApp />         
    // <Top5CDApp />         
}    
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
