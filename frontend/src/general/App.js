import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Link, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Algorithms from './ApplyAlgorithms.js';
import Merge from './Merge.js';
import Voting from './Voting.js';
import Home from './Home.js';

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
                    <li style={{'width':'270px'}}><Link to="/">HOME</Link></li>
                    <li><Link to="/classics">Classic algorithms</Link></li>
                    <li><Link to="/merge">Merge algorithms results</Link></li>
                    <li><Link to="/voting">Voting algorithm</Link></li>
                </ul>   
            </div>

            <Switch>
                <Route exact path="/classics">
                    <Algorithms/>
                </Route>
                <Route exact path="/">
                    <Home/>
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
