import React from 'react';
import RefreshIcon from '@material-ui/icons/Refresh';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import './Tile.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import ImageUploader from 'react-images-upload'; 

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
                <p>{this.state.title}</p>
                <table className="Table">
                    <tr className="Buttons">
                        {/* <td>
                            <button type="submit" onClick={this.load} className="RefreshButton"><RefreshIcon/></button>
                        </td> */}
                        <td>
                            <ImageUploader
                                className="Upload"
                                withIcon={false}
                                withLabel={false}
                                buttonText={<BackupOutlinedIcon/>}
                                singleImage={true}
                                onChange={this.upload}
                                imgExtension={['.jpg', '.png']}
                                maxFileSize={5242880}
                            />
                        </td>
                    </tr>
                </table>
                {/* <img    src={this.state.loading ? "https://i.pinimg.com/originals/a2/dc/96/a2dc9668f2cf170fe3efeb263128b0e7.gif" : this.state.result} */}
                <img    src={this.state.loading ? "https://i.pinimg.com/originals/25/ef/28/25ef280441ad6d3a5ccf89960b4e95eb.gif" : this.state.result}
                        onClick={this.onImageClick}
                        className={this.state.open ? 'magnified' : 'small'}/>
            </div>
        );
    }
}

export default Tile;
