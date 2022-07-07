import express from "express";
import cors from "cors";
import bodyParser from "body-parser";

const app = express();
import dotenv from 'dotenv';
dotenv.config();

import axios from "axios";

//Middleware
app.use(bodyParser.json());
app.use(cors());

app.get("/", (req, res) => {
  const body = req.body;

  const headers = {
    "Content-Type": "application/json",
    Authorization:
      process.env.AUTH,
  };

  axios
    .get(
      process.env.DB_URL
    )
    .then(async (resToken) => {
      const { data } = resToken;
      console.log(data);
      let availableTokens = [];
      for (const key in data) {
        availableTokens.push(data[key].name);
      }

      const body1 = JSON.stringify({
        data: {
          data: {
            id: Math.floor(Math.random() * 1000000) + 1,
            type: body.type ? body.type : "COMPLEX",
            title:
              body.title && body.title != "" ? body.title : "Message to you",
            subTitle:
              body.subTitle && body.subTitle != ""
                ? `From ${body.subTitle}`
                : "From anonymous",
            img:
              body.img && body.img != ""
                ? body.img
                : "https://w0.peakpx.com/wallpaper/519/793/HD-wallpaper-cats-landscape-mountains-water-rocks-trees-animals.jpg",
            contentTitle:
              body.contentTitle && body.contentTitle != ""
                ? body.contentTitle
                : "Message",
            content:
              body.content && body.content != ""
                ? body.content
                : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis interdum neque, eu sodales nunc vestibulum in. Cras lacinia facilisis est, eget interdum est tempus vitae. Fusce pulvinar imperdiet ipsum, eu pellentesque ligula sagittis sit amet.",
          },
        },
        registration_ids: availableTokens,
      });

      // Message
      axios
        .post("https://fcm.googleapis.com/fcm/send", body1, {
          headers: headers,
        })
        .then((response) => {
          res.send("Message sent successfully");
        })
        .catch((error) => {
          res.send("Error message");
        });
    })
  .catch(function (error) {
    res.send("Error db");
  });
});

app.listen(process.env.PORT || 2000);
