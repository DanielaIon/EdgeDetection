import React from 'react';
import './Voting.css';
import 'bootstrap/dist/css/bootstrap.min.css';  
import BackupOutlinedIcon from '@material-ui/icons/BackupOutlined';
import ExpandLessIcon from '@material-ui/icons/ExpandLess';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import ImageUploader from 'react-images-upload'; 
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Spinner from 'react-bootstrap/Spinner'


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
            expanded: true,
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
                        <Accordion defaultActiveKey="0">
                            <Card>
                                <Card.Header>
                                <Accordion.Toggle as="div" variant="link" eventKey="0" 
                                    onClick={(e) => this.setState({expanded: !this.state.expanded})}>
                                    {this.state.expanded
                                        ? <ExpandLessIcon className="uploadIcon2"/>
                                        : <ExpandMoreIcon className="uploadIcon2"/>}
                                    Edge Detection Strategies
                                </Accordion.Toggle>
                                </Card.Header>
                                <Accordion.Collapse eventKey="0">
                                <Card.Body>
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
                                    <hr/>
                                </Card.Body>
                                </Accordion.Collapse>
                            </Card>
                        </Accordion>
                        <br/>
                        <div>
                            Trust Adjustment Percent
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
                            Binarization Threshold Strategy
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
                            Trust Threshold
                            <br/>
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
                
            <div className="ElectionPictures">
                {this.state.imgUrl && 
                (!this.state.loading
                    ?<img
                        className="ElectionResult"
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
    );
  }
}

export default Voting;
