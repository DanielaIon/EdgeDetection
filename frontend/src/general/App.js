import React from 'react';
import './App.css';
import './UploadImage.css';
import { BrowserRouter, Switch, Link, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';  
import Algorithms from './ApplyAlgorithms.js';
import Merge from './Merge.js';
import Voting from './Voting.js';
import Home from './Home.js';
import Timeline from './Timeline.js';
import Logo from '../resources/horizon_white.png';


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
                    <li style={{'width':'300px'}}>
                        <Link to="/">
                            <img src={Logo} width={200}/>
                        </Link>
                    </li>
                    <li><Link to="/classics">Classic algorithms</Link></li>
                    <li><Link to="/merge">Merge algorithms results</Link></li>
                    <li><Link to="/voting">Voting algorithm</Link></li>
                    <li><Link to="/timeline">Timeline</Link></li>
                </ul>   
            </div>
            <br/>

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

                <Route exact path="/timeline">
                    <Timeline/>
                </Route>

                
            </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
