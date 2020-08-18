import React from 'react';
// import logo from './girl.jpg';
import './App.css';
import { BrowserRouter, Switch, Link, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Algorithms from './ApplyAlgorithms.js';
import Merge from './Merge.js';
import Voting from './Voting';

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
                    <li><Link to="/home">HOME</Link></li>
                    <li><Link to="/">Apply chosen algorithms</Link></li>
                    <li><Link to="/merge">Merge algorithm result</Link></li>
                    <li><Link to="/voting">Vote</Link></li>
                </ul>   
            </div>

            <Switch>
                <Route exact path="/">
                    {/* <Algorithms/> */}
                    <Algorithms/>
                </Route>

                <Route exact path="/merge">
                    <Merge/>
                </Route>

                <Route exact path="/voting">
                    <Voting/>
                </Route>
            </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
