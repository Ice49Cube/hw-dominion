using System;
using System.Collections.Generic;
using System.Net;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

using HtmlAgilityPack;

namespace DownDomCards
{
    class Program
    {
        private const string TARGET_FOLDER = @"C:\dominion_images\download";

        static void Main(string[] args)
        {
            EnumFolders(new Uri("http://wiki.dominionstrategy.com/images/"));
            Console.ReadKey();
        }

        private static void EnumFolders(Uri uri)
        {
            var document = DownloadDocument(uri);
            Console.WriteLine(uri.ToString());
            if (document == null) return;
            var table = document.DocumentNode.SelectSingleNode("//html/body/table");
            if (table == null) return;
            var rows = table.SelectNodes("tr");
            if (rows == null) return;
            foreach (var row in rows)
            {
                var cells = row.SelectNodes("td");
                if (cells == null) continue;
                var img = cells[0].SelectSingleNode("img");
                if (img == null) continue;
                var alt = img.GetAttributeValue("alt", null);
                var anchor = cells[1].SelectSingleNode("a");
                if (anchor == null) continue;
                var href = anchor.GetAttributeValue("href", null);
                href = HttpUtility.UrlDecode(href);
                if (href == null || href == "/") continue;
                Uri result;
                if (!Uri.TryCreate(uri, href, out result)) continue;
                if (result.ToString().Length <= uri.ToString().Length) continue;
                if (result.Segments.Length < 3 || result.Segments[2].Length != 2) continue;
                if (alt == "[DIR]")
                {
                    EnumFolders(result);
                }
                else if (alt == "[IMG]")
                {
                    DownloadFile(result);
                }
            }
        }

        private static void DownloadFile(Uri result)
        {
            var offset = result.ToString().IndexOf("/images/");
            if (offset < 0) return;
            var relative = result.ToString().Substring(offset + 1).Replace("/", @"\");
            var local = System.IO.Path.Combine(TARGET_FOLDER, relative);
            var folder = System.IO.Path.GetDirectoryName(local);
            if (System.IO.File.Exists(local)) return;
            if (!System.IO.Directory.Exists(folder))
                System.IO.Directory.CreateDirectory(folder);
            using (var wc = new WebClient())
                wc.DownloadFile(result, local);
        }

        private static HtmlDocument DownloadDocument(Uri uri)
        {
            using (var wc = new WebClient())
            {
                var html = wc.DownloadString(uri);
                var document = new HtmlDocument();
                document.LoadHtml(html);
                return document;
            }
        }
    }


}