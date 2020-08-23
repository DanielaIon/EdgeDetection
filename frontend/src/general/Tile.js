import React from 'react';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import './Tile.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import ImageUploader from 'react-images-upload'; 
import Spinner from 'react-bootstrap/Spinner'


const axios = require('axios');

class Tile extends React.Component {
 
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
   
    upload = (e) => {
        console.log(e[0]);
        let file = e[0]
        this.setState({img: file});

        this.setState({loading: true});

            const formData = new FormData();
            formData.append( 
                "img", 
                file 
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
            <div className="tile">
                <p>{this.state.title} </p> 
                {!this.state.loading 
                    ?<div>
                        <ImageUploader
                            className="Upload"
                            withIcon={false}
                            withLabel={false}
                            buttonText={<BackupOutlinedIcon/>}
                            singleImage={true}
                            onChange={this.upload}
                            imgExtension={['.jpg', '.png', '.jpeg']}
                            maxFileSize={5242880}
                        />
                        <div className={this.state.open ? 'tile-container' : 'none'}>
                            <img    src={this.state.result}
                                    onClick={this.onImageClick}
                                    className={this.state.open ? 'magnified' : 'small'}/>
                        </div>
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

export default Tile;
