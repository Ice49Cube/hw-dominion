using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
//using System.Text;
using System.Threading.Tasks;

namespace BatchResize
{
    class Program
    {
        static void Main(string[] args)
        {
            long quality = 90;
            if(args.Length == 0 
            || !Directory.Exists(args[0]))
            {
                return;
            }

            int width, height;
            if(args.Length >= 3)
            {
                width = int.Parse(args[1]);
                height = int.Parse(args[2]);
                if (args.Length >= 4)
                {
                    quality = int.Parse(args[3]);
                }
            }
            else
            {
                width = 345;
                height = 552;
            }
            var inFolder = new DirectoryInfo(args[0]);
            var count = 0;
            var outFolderName = "output";
            while (Directory.Exists(Path.Combine(inFolder.FullName, outFolderName)))
                outFolderName = "output" + ++count;
            Directory.CreateDirectory(Path.Combine(inFolder.FullName, outFolderName));
			FileInfo[] files = inFolder.GetFiles("*.jpg");
			bool isPng = false;
			if (files.Length == 0) {
				isPng = true;
				files = inFolder.GetFiles("*.png");
			}
            foreach(var file in files)
            {
                using (Image image = Image.FromFile(file.FullName))
                {
                    using (var bmp = new Bitmap(width, height))
                    {
                        using (var g = Graphics.FromImage(bmp))
                        {
                            g.DrawImage(image, 0, 0, (int)(width), (int)(height));
                        }
						var filename = Path.Combine(inFolder.FullName, outFolderName, file.Name);
						if(isPng) {
							bmp.MakeTransparent(bmp.GetPixel(0,0));     // Change a color to be transparent
							((Image) bmp).Save(filename, ImageFormat.Png);  // Correct PNG save
						} else {
							var codec = GetEncoder(ImageFormat.Jpeg);
							var encoder = Encoder.Quality;
							var parameter = new EncoderParameter(encoder, quality );
							var parameters = new EncoderParameters(1);
							parameters.Param[0] = parameter;
							bmp.Save(filename, codec, parameters );
						}
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
