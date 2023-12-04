const tf = require('@tensorflow/tfjs-node');
const Jimp = require('jimp');

exports.getPrediction = async (req, res) => {
  try {
    const model = await tf.loadLayersModel('file://app/tfjs/model.json');
    const image = await Jimp.read(req.file.buffer);
    image.cover(150, 150, Jimp.HORIZONTAL_ALIGN_CENTER | Jimp.VERTICAL_ALIGN_MIDDLE);

    const NUM_OF_CHANNELS = 3;
    let values = new Float32Array(150 * 150 * NUM_OF_CHANNELS);

    let i = 0;
    image.scan(0, 0, image.bitmap.width, image.bitmap.height, (x, y, idx) => {
      const pixel = Jimp.intToRGBA(image.getPixelColor(x, y));
      pixel.r = pixel.r / 127.0 - 1;
      pixel.g = pixel.g / 127.0 - 1;
      pixel.b = pixel.b / 127.0 - 1;
      pixel.a = pixel.a / 127.0 - 1;
      values[i * NUM_OF_CHANNELS + 0] = pixel.r;
      values[i * NUM_OF_CHANNELS + 1] = pixel.g;
      values[i * NUM_OF_CHANNELS + 2] = pixel.b;
      i++;
    });

    const outShape = [150, 150, NUM_OF_CHANNELS];
    let img_tensor = tf.tensor3d(values, outShape, 'float32');
    img_tensor = img_tensor.expandDims(0);

    const prediction = await model.predict(img_tensor).dataSync();
    res.send({ message: prediction });
  } catch (error) {
    if (error instanceof Error) {
      res.status(500).send({ message: error.message });
    } else {
      res.status(500).send({ message: 'An unknown error occurred.' });
    }
  }
};