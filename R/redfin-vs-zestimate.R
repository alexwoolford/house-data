# Title     : TODO
# Objective : TODO
# Created by: awoolford
# Created on: 9/30/17

library(RMySQL)
library(ggplot2)
library(ggrepel)
library(scales)

con <- dbConnect(RMySQL::MySQL(), user="root", password="********", host="deepthought", dbname = "house_data")
data <- dbReadTable(con, "house_data")
dbDisconnect(con)

data <- subset(data, price < 700000)

ggplot(data, aes(x=price, y=rentZestimate)) +
    geom_point(aes(price, rentZestimate), color = 'red') +
    geom_text_repel(aes(x=price, y=rentZestimate, label=address)) +
    scale_x_continuous(labels=dollar) +
    scale_y_continuous(labels=dollar) +
    geom_smooth(method=lm) +
    labs(x="Redfin price", y="Zillow Rent Zestimate")

ggsave("redfin_vs_zestimate.pdf", width=7, height=6)
