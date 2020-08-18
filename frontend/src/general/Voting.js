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

            trustReevaluationOptions: [],
            trustReevaluation: null,

            binarizationThresholdOptions: [],
            binarizationThreshold: null,

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
                this.setState({
                    trustReevaluationOptions: res.data.sort()
                });
            }).catch((error) => {
                console.log(error);
            });
        
        axios.get('http://localhost:8080/voting/binarization-threshold')
            .then((res) => {
                this.setState({
                    binarizationThresholdOptions: res.data.sort()
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
        console.log(this.state.voters);
        console.log(this.state.trust);
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
        this.setState({
            onTrustThresholdChange: parseFloat(e.target.value)
        });
    }

    render() {
    
    return (
        <div className="App">
            {!this.state.imgUrl   
                ?   <div>
                        <UploadImage onFileChange={this.onFileChange}/>
                        Edge Detection Strategies:
                        {this.state.voterOptions.map((data, idx) => 
                            <div key={idx}>
                                <input  type="checkbox"
                                        checked={this.state.voters.indexOf(data) >= 0}
                                        onChange={this.onCheckBoxClicked(data)}
                                />
                                {data}
                                {this.state.voters.indexOf(data) >= 0 &&
                                    <input  type="number"
                                            step="any"
                                            value={this.state.trust[this.state.voters.indexOf(data)]}
                                            onChange={this.onTrustChange(data)}/>
                                }
                            </div>
                        )}
                        <div>
                            Trust Reevaluation Strategy:
                            <select onChange={this.onTrustReevaluationChange}>
                                <option hidden disabled selected value>
                                    -- select an option --
                                </option>
                                {this.state.trustReevaluationOptions.map((op, idx) => 
                                    <option value={op} key={idx}>
                                        {op}
                                    </option> 
                                )}
                            </select>
                        </div>
                        <div>
                            Binarization Threshold Strategy:
                            <select onChange={this.onBinarizationThresholdChange}>
                                <option hidden disabled selected value>
                                    -- select an option -- 
                                </option>
                                {this.state.binarizationThresholdOptions.map((op, idx) => 
                                    <option value={op} key={idx}>
                                        {op}
                                    </option> 
                                )}
                            </select>
                        </div>
                        <div>
                            Trust Threshold:
                            <input  type="number"
                                    step="any"
                                    value={this.state.trustThreshold}
                                    onChange={this.onTrustThresholdChange}/>
                        </div>
                    </div>
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

export default Voting;
