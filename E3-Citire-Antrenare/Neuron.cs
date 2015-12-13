using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

namespace RNpredictivClass
{
    public class Neuron
    {
        private double[] w;
        private double[] val;
        private const double Eta = 0.01;
        private readonly int k;

        public Neuron(int n)
        {
            k = n;
        }

        public void SetValues(string filename,double range)
        {
            val = Read(filename);
            w = GenerateRandom(k, range);
        }

        public void Antrenare()
        {
            double eg;
            double global = 0;
            var output = new double[val.Length];
            do
            {
                for (int i = 0; i < val.Length - k; i++)
                {
                    var fer = val.Skip(i).Take(k).ToArray();
                    var result = fer.Zip(w, (a, b) => a*b).ToArray().Sum();
                    var err = result - val[i + 1];

                    for (int j = 0; j < k; j++)
                    {
                        w[j] = w[j] - Eta*val[j]*err;
                    }
                }
                eg = 0;
                for (int i = 0; i < val.Length - k; i++)
                {
                    var fer = val.Skip(i).Take(k).ToArray();
                    var result = fer.Zip(w, (a, b) => a*b).ToArray().Sum();
                    var err = result - val[i + 1];
                    eg = eg + Math.Abs(err);
                    output[i] = result;
                }
                global = (global + eg)/2;
            } while (Math.Abs(eg - global) > 0);
            Write(output);
        }

        private double Rand(double range)
        {
            Random random = new Random();
            return random.NextDouble() * (range - (-1 * range)) + (-1 * range);
        }

        private double[] Read(string filename)
        {
            List<double> values = new List<double>();
            using (TextReader reader = File.OpenText(filename))
            {
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    try
                    {
                        values.Add(double.Parse(line, CultureInfo.InvariantCulture));
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.Message);
                    }
                }
            }
            return values.ToArray();
        }

        private void Write(IEnumerable<double> output)
        {
            StreamWriter file;
            using (file = new StreamWriter("Output.txt"))
            {
                foreach (var d in output)
                {
                    file.WriteLine(d.ToString(CultureInfo.InvariantCulture));
                }
            }
        }

        public void ParseWav(string filename)
        {
            BinaryReader reader = new BinaryReader(new MemoryStream(File.ReadAllBytes(filename)));
            int chunkId = reader.ReadInt32();
            int fileSize = reader.ReadInt32();
            int riffType = reader.ReadInt32();
            int fmtId = reader.ReadInt32();
            int fmtSize = reader.ReadInt32();
            int fmtCode = reader.ReadInt16();
            int fmtChannels = reader.ReadInt16();
            int fmtSampleRate = reader.ReadInt32();
            int fmtAvgBps = reader.ReadInt32();
            int fmtBlockAlign = reader.ReadInt16();
            int fmtBitDepth = reader.ReadInt16();

            if (fmtSize == 18)
            {
                int fmtExtraSize = reader.ReadInt16();
                reader.ReadBytes(fmtExtraSize);
            }

            int dataId = reader.ReadInt32();
            int dataSize = reader.ReadInt32();
            byte[] dataBytes = reader.ReadBytes(dataSize);

            Console.WriteLine("chunkID :" + chunkId);
            Console.WriteLine("fileSize: " + fileSize);
            Console.WriteLine("riffType: " + riffType);
            Console.WriteLine("ID: " + fmtId);
            Console.WriteLine("SampleRate: " + fmtSampleRate);
            Console.WriteLine("BitDepth: " + fmtBitDepth);
            Console.WriteLine("Channels: " + fmtChannels);
            Console.WriteLine("Average BPS : " + fmtAvgBps);
            
            Console.WriteLine("Content:");
            foreach (var dataByte in dataBytes)
            {
                Console.WriteLine(dataByte);
            }
        }

        private double[] GenerateRandom(int length, double range)
        {
            double[] r = new double[length];

            for (int i = 0; i < length; i++)
            {
                r[i] = Rand(range);
            }
            return r;
        }
    }
}
