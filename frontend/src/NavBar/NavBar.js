import React from 'react';
import './NavBar.css';

function NavBar() {
  return (
    <div className='container-fluid body' >
      <ul className="NavBar">
        <li><a href="/merge">Merge</a></li>
        <li><a href="/algorithms">Algorithms</a></li>
      </ul>   
    </div>
  );
}

export default NavBar;
