import React from 'react';
// import logo from './girl.jpg';
import './App.css';
import { BrowserRouter, Switch, Link, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Algorithms from './Algorithms.js';
import Merge from './Merge.js';

class App extends React.Component {
 
  constructor(props) {
      super(props);

      this.state = {
        
      };
  }

  render() {
    
    return (
      <div className="App">
        <BrowserRouter basename="/">
             {/* navbar */}
            <div className='container-fluid body' >
                <ul className="NavBar">
                    <li><Link to="/">Algorithms</Link></li>
                    <li><Link to="/merge">Merge</Link></li>
                </ul>   
            </div>

            <Switch>
                <Route exact path="/">
                    <Algorithms/>
                </Route>

                <Route exact path="/merge">
                    <Merge/>
                </Route>
            </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
