import React from 'react';
import './Merge.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import ImageUploader from 'react-images-upload'; 
import Spinner from 'react-bootstrap/Spinner'

const axios = require('axios');

class Algorithms extends React.Component {
 
  constructor(props) {
      super(props);
      this.state = {
        img: null,
        imgUrl: null,
        result: null,
        open: false,
        loading: false,
        options: [],
        selectedOptions: [],
      };
  }

  componentDidMount() {
    axios.get('http://localhost:8080/merge')
      .then((res) => {
        this.setState({
          options: res.data,
          selectedOptions: res.data,
        });
      }).catch((error) => {
        console.log(error);
      });
  }

  onCheckBoxClicked = (value) => {
    return (e) => {
      if (e.target.checked) {
        this.setState({
          selectedOptions: [...this.state.selectedOptions, value]
        });
      } else {
        this.setState({
          selectedOptions: this.state.selectedOptions.filter(o => o !== value)
        });
      }
    }
  }

  onFileChange = (e) => {
    let file = e[0]
    let url = URL.createObjectURL(file);
    this.setState({
      img: file,
      imgUrl: url,
      loading: true
    });

    const formData = new FormData();
    formData.append( 
      "img",
      file 
    );
    formData.append( 
      "options",
      JSON.stringify(this.state.selectedOptions)
    );

    axios.post('http://localhost:8080/merge', formData, {headers: {"Content-type": "multipart/form-data"}})
      .then((res) => {
        let result = "data:image/png;base64," + res.data;
        this.setState({result: result, loading: false});
      }).catch((error) => {
        console.log(error);
      });
  }

  onSelectAll = (e) => {
    if (e.target.checked) {
      this.setState({
        selectedOptions: this.state.options
      });

    } else {
      this.setState({
        selectedOptions: []
      });
    }
  }
  

  render() {
    
    return (
      <div className="App">
        <div className="DivContainer">
          <div className="Options">
            <div>
              <label className="container">
                Select all
                <input
                  type="checkbox"
                  checked={this.state.selectedOptions.length === this.state.options.length}
                  onClick={this.onSelectAll}
              />
              <span className="checkmark"></span>
              </label>
            </div>  
            {this.state.options.map(op => 
              <div>
                <label className="container">
                  {op}
                  <input
                    type="checkbox"
                    checked={this.state.selectedOptions.filter(sop => op === sop).length > 0}
                    onClick={this.onCheckBoxClicked(op)}
                />
                <span className="checkmark"></span>
                </label>
              </div> 
            )} 

            <div height="0px">
              <ImageUploader
                      className="Upload"
                      withIcon={false}
                      withLabel={false}
                      buttonText={<div> <BackupOutlinedIcon/>  {" Upload image "}</div>}
                      singleImage={true}
                      onChange={this.onFileChange}
                      imgExtension={['.jpg', '.png', '.jpeg']}
                      maxFileSize={5242880}
              />
              <div className="OptionsPictureContainer">
                {this.state.imgUrl && <img src={this.state.imgUrl} className="OptionsPicture"/>}
              </div>
            </div>
            <br/>
          </div>
          <div className="MergedPictures">
            {this.state.imgUrl && 
              (!this.state.loading
                ?<img
                  className="MergedPicture"
                  src={this.state.result}
                />
                :<div className="LoadingImage">
                    <br/>
                    <Spinner animation="border" className="spinner"/>
                </div>
              )
            }
          </div>
        </div>
      </div>
    );
  }
}

export default Algorithms;
