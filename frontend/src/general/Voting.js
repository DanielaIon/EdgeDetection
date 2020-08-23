import React from 'react';
// import logo from './girl.jpg';
import './Voting.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import RefreshIcon from '@material-ui/icons/Refresh';
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import UploadImage from './UploadImage';
import ImageUploader from 'react-images-upload'; 

const axios = require('axios');

class Voting extends React.Component {
 
    constructor(props) {
        super(props);
        this.state = {
            img: null,
            imgUrl: null,
            result: null,

            voterOptions: [],
            voters: [],
            trust: [],

            trustReevaluation: 5,
            binarizationThreshold: 128,

            trustThreshold: 0.5,
            
            open: false,
            loading: false,
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8080/voting/voters')
            .then((res) => {
                let results = res.data.sort();
                this.setState({
                    voterOptions: results,
                    voters: results,
                    trust: results.map(d => 1.0)
                });
            }).catch((error) => {
                console.log(error);
            });

        axios.get('http://localhost:8080/voting/trust-reevaluations')
            .then((res) => {
                const aux = res.data.sort();
                this.setState({
                    trustReevaluationOptions: aux,
                    trustReevaluation: aux[0]
                });
            }).catch((error) => {
                console.log(error);
            });
        

        axios.get('http://localhost:8080/voting/binarization-threshold')
            .then((res) => {
                const aux = res.data.sort();
                this.setState({
                    binarizationThresholdOptions: aux,
                    binarizationThreshold: aux[0]
                });
            }).catch((error) => {
                console.log(error);
            });
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
            "voters",
            JSON.stringify(this.state.voters.reduce((map, v) => {
                map[v] = this.state.trust[this.state.voters.indexOf(v)];
                return map;
            }, {}))
        );
        formData.append( 
            "trustReevaluation",
            this.state.trustReevaluation
        );
        formData.append( 
            "binarizationThreshold",
            this.state.binarizationThreshold
        );
        formData.append( 
            "trustThreshold",
            this.state.trustThreshold
        );

        axios.post('http://localhost:8080/voting', formData, {headers: {"Content-type": "multipart/form-data"}})
            .then((res) => {
                let result = "data:image/png;base64," + res.data;
                this.setState({result: result, loading: false});
            }).catch((error) => {
                console.log(error);
            });
    }

    onCheckBoxClicked = (value) => {
        return (e) => {
            if (e.target.checked) {
                this.setState({
                    voters: [...this.state.voters, value],
                    trust: [...this.state.trust, 1.0],
                });
            } else {
                let idx = this.state.voters.indexOf(value);
                this.setState({
                    voters:  [
                        ...this.state.voters.slice(0, idx),
                        ...this.state.voters.slice(idx + 1)
                    ],
                    trust: [
                        ...this.state.trust.slice(0, idx),
                        ...this.state.trust.slice(idx + 1)
                    ],
                });
            }
        }
    }

    onTrustChange = (value) => {
        return (e) => {
            let idx = this.state.voters.indexOf(value);
            this.setState({
                trust: [
                    ...this.state.trust.slice(0, idx),
                    parseFloat(e.target.value),
                    ...this.state.trust.slice(idx + 1)
                ],
            });
        }
    }

    onTrustReevaluationChange = (e) => {
        this.setState({
            trustReevaluation: e.target.value
        });
    }

    onBinarizationThresholdChange = (e) => {
        this.setState({
            binarizationThreshold: e.target.value
        });
    }

    onTrustThresholdChange = (e) => {
        console.log(e.target)
        this.setState({
            trustThreshold: parseFloat(e.target.value)
        });
    }

    render() {
    
    return (
        <div className="App">
                <div  className="Options">
                    <div>
                        Edge Detection Strategies:
                        {this.state.voterOptions.map((data, idx) => 
                            <div key={idx}>
                                <div className="VoteCheckbox">
                                    <label className="container">
                                        <input  type="checkbox"
                                                checked={this.state.voters.indexOf(data) >= 0}
                                                onChange={this.onCheckBoxClicked(data)}
                                        />

                                        <span className="checkmark"></span>
                                    </label> 
                                </div>

                                {'\u00A0'}{'\u00A0'}

                                {this.state.voters.indexOf(data) >= 0 &&
                                    <input  type="number"
                                            step="any"
                                            value={this.state.trust[this.state.voters.indexOf(data)]}
                                            onChange={this.onTrustChange(data)}
                                            className="StateTrustNumber"/>
                                }
                                
                                <div className="LabelStyle">
                                    {data}
                                </div>

                            </div>
                        )}
                    </div>

                    <br/>
                    
                    <div>
                        Trust Adjustment Percent:
                        <br/>
                        <input  className="slider" id="trustAdjustmentPercent"
                                type="range" min="1" max="50"
                                value={this.state.trustReevaluation}
                                onChange={this.onTrustReevaluationChange}
                        />
                        {this.state.trustReevaluation}
                    </div>
                       
                    <br/>

                    <div>
                        Binarization Threshold Strategy:
                        <br/>
                        <input  className="slider" id="trustAdjustmentPercent"
                                type="range" min="1" max="255"
                                value={this.state.binarizationThreshold}
                                onChange={this.onBinarizationThresholdChange}
                        />
                        {this.state.binarizationThreshold}
                    </div>

                    <br/>

                    <div>
                        Trust Threshold:
                        <br/>
                        {/* <input  className="Inputs"
                                type="number"
                                step="any"
                                value={this.state.trustThreshold}
                                onChange={this.onTrustThresholdChange}/> */}
                        <input  className="slider" id="trustAdjustmentPercent"
                                type="range" min="0" max="1" step="0.01"
                                value={this.state.trustThreshold}
                                onChange={this.onTrustThresholdChange}
                        />
                        {this.state.trustThreshold * 100}%
                    </div>

                    <br/>
                    
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
                
            <div>
                {this.state.imgUrl  &&
                    <div className="ElectionPictures">
                        <img src={this.state.loading ? "https://i.pinimg.com/originals/58/4b/60/584b607f5c2ff075429dc0e7b8d142ef.gif" : this.state.result}
                            className="ElectionResult"/>
                    </div>
                }
            </div>
        </div>
    );
  }
}

export default Voting;
