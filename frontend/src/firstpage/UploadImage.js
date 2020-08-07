import React from 'react';
import ImageUploader from 'react-images-upload';

import './UploadImage.css';

function UploadImage(props) {

    return (
        <header className="App-header">
            <center>
                <table border="0" className="App-table">
                  <tbody>
                    <tr>
                      <td className="App-menu">
                      <ImageUploader
                          className="Upload"
                          style={{'width':'100px'}}
                          withIcon={false}
                          withLabel={false}
                          buttonText='Choose image'
                          singleImage={true}
                          onChange={props.onFileChange}
                          imgExtension={['.jpg', '.png']}
                          maxFileSize={5242880}
                      />
                      </td>
                    </tr>
                  </tbody>
                </table>
            </center>
        </header>
    );
}

export default UploadImage;