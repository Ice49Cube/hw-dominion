using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;


namespace BatchCut
{
    class Program
    {
        [STAThread()]
        static void Main(string[] args)
        {
            if(args.Length != 5)
                return;
            if (!Directory.Exists(args[0]))
                return;

            var x = int.Parse(args[1]);
            var y = int.Parse(args[2]);
            var width = int.Parse(args[3]);
            var height = int.Parse(args[4]);

            string folder = Path.Combine(args[0], "output");
            var count = 0;
            while(Directory.Exists(folder))
                folder = Path.Combine(args[0], "output" + (++count).ToString());
            Directory.CreateDirectory(folder);
            foreach (var filename in Directory.GetFiles(args[0], "*.jpg"))
            {
                using (Image image = Image.FromFile(filename))
                {
                    using (var bmp = new Bitmap(width-x, height-y))
                    {
                        using (var g = Graphics.FromImage(bmp))
                        {
                            g.DrawImage(image, new Rectangle(0, 0, width-x, height-y), new Rectangle(x, y, width-x, height-y), GraphicsUnit.Pixel);
                        }
                        var codec = GetEncoder(ImageFormat.Jpeg);
                        var encoder = Encoder.Quality;
                        var parameter = new EncoderParameter(encoder, 1);
                        var parameters = new EncoderParameters(100);
                        parameters.Param[0] = parameter;
                        var target = Path.Combine(folder, Path.GetFileName(filename));
                        //bmp.Save(target, codec, parameters);
                        bmp.Save(target);
                        
                    }
                }
            }
        }

        private static ImageCodecInfo GetEncoder(ImageFormat format)
        {

            ImageCodecInfo[] codecs = ImageCodecInfo.GetImageDecoders();

            foreach (ImageCodecInfo codec in codecs)
            {
                if (codec.FormatID == format.Guid)
                {
                    return codec;
                }
            }
            return null;
        }
    }

}
