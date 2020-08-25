import React from 'react';
import './Tile.css'
import 'bootstrap/dist/css/bootstrap.min.css';  
import Spinner from 'react-bootstrap/Spinner'


const axios = require('axios');

class TimelineTile extends React.Component {
 
    constructor(props) {
        super(props);
        this.state = {
            title: props.title,
            reqUrl: props.reqUrl,
            img: props.img,
            result: null,
            open: false,
            loading: false
        };
    }

    componentDidMount() {
        this.load()
    }

    load = () => {
        this.setState({loading: true});

        const formData = new FormData();
        formData.append( 
            "img", 
            this.state.img 
        );

        axios.post(this.state.reqUrl, formData, {headers: {"Content-type": "multipart/form-data"}})
        .then((res) => {
            let result = "data:image/png;base64," + res.data;
            this.setState({result: result, loading: false});
        }).catch((error) => {
            console.log(error);
        });
    }

    onImageClick = (e) => {
        e.preventDefault();
        this.setState({open: !this.state.open});
    }
    
    render() {
        return (
            <div>
                {!this.state.loading 
                    ? 
                    <div className={this.state.open ? 'tile-container' : 'none'}>
                        <img    src={this.state.result}
                                onClick={this.onImageClick}
                                className={this.state.open ? 'magnified' : 'TimelineImage'}/>
                    </div>
                    :
                    <div className="LoadingImage">
                        <br/>
                        <Spinner animation="border" className="spinner"/>
                    </div>}
            </div>
        );
    }
}

export default TimelineTile;
