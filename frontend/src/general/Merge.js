import React from 'react';
// import logo from './girl.jpg';
import './ApplyAlgorithms.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import RefreshIcon from '@material-ui/icons/Refresh';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import UploadImage from './UploadImage';
import ImageUploader from 'react-images-upload'; 

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
        console.log(res.data);
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

    console.log(this.state.selectedOptions);

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

  render() {
    
    return (
      <div className="App">
        <div>
          {this.state.options.map(op => 
            <div>
              <input
                  type="checkbox"
                  checked={this.state.selectedOptions.filter(sop => op === sop).length > 0}
                  onClick={this.onCheckBoxClicked(op)}
              />
              <label>
                {op}
              </label>
            </div>  
          )}
        </div>
        {!this.state.imgUrl   
          ? <UploadImage onFileChange={this.onFileChange}/>
          : <div className="tile">
              <p>{this.state.title}</p>
              <table className="Table">
                  <tr className="Buttons">
                      <td>
                          <button type="submit" onClick={this.load} className="RefreshButton"><RefreshIcon/></button>
                      </td>
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
              <img    src={this.state.loading ? "https://i.pinimg.com/originals/a2/dc/96/a2dc9668f2cf170fe3efeb263128b0e7.gif" : this.state.result}
                      onClick={this.onImageClick}
                      className={this.state.open ? 'magnified' : 'small'}/>
            </div>
            
        }
      </div>
    );
  }
}

export default Algorithms;
