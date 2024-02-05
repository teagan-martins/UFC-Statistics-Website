from bs4 import BeautifulSoup
import pandas as pd
import requests
import string

alphabet = list(string.ascii_lowercase)

df1 = pd.DataFrame({'Fighter ID': [], 'First': [], 'Last': [], 'Nickname': [],'Height':[],'Weight':[], 'Reach':[],'Stance':[],'Wins':[],'Losses':[],'Draws':[],'Belt':[]})

for letter in alphabet:
    html = requests.get('http://ufcstats.com/statistics/fighters?char={}&page=all'.format(letter)).text
    soup = BeautifulSoup(html, 'lxml')
    tr = soup.find_all('a', class_ = 'b-link b-link_style_black')
    td = soup.find_all('td', class_ = 'b-statistics__table-col')

    count = 0
    for x in tr:
        tr[count] = str(tr[count])
        index1 = tr[count].find('>')
        index2 = tr[count].rfind('<')
        tr[count] = tr[count][index1+1:index2]
        count += 1

    count = 0

    fighter_ids = []
    first_names = []
    last_names = []
    nicknames = []

    for x in tr:
        if (count % 3) == 0:
            first_names.append(tr[count])
            last_names.append(tr[count+1])
            nicknames.append(tr[count+2])
        count += 1

    fid = 1
    for x in first_names:
        fighter_ids.append(fid)
        fid += 1

    count = 0

    for x in td:
        td[count] = str(td[count])
        count += 1

    count = 0

    for x in td:
        if 'href' in td[count]:
            del td[count]
        count+=1

    count = 0

    for x in td:
        if 'href' in td[count]:
            del td[count]
        count+=1

    count = 0

    for x in td:
        index1 = td[count].find('\n')
        index2 = td[count].rfind('\n')
        td[count] = td[count][index1+1:index2]
        td[count] = td[count].strip()
        td[count] = td[count].rstrip('.')
        count += 1

    heights = []
    weights = []
    reaches = []
    stances = []
    wins = []
    losses = []
    draws = []
    belt = []

    count = 0

    for x in td:
        if (count % 8) == 0:
            heights.append(td[count])
            weights.append(td[count+1])
            reaches.append(td[count+2])
            stances.append(td[count+3])
            wins.append(td[count+4])
            losses.append(td[count+5])
            draws.append(td[count+6])
            belt.append(td[count+7])

        count += 1

    count = 0
    for x in belt:
        if "img" in str(belt[count]):
            belt[count] = 'Yes'
        count += 1

    df2 = pd.DataFrame({'Fighter ID': fighter_ids,'First': first_names, 'Last': last_names, 'Nickname': nicknames,'Height':heights,'Weight':weights, 'Reach':reaches,'Stance':stances,'Wins':wins,'Losses':losses,'Draws':draws,'Belt':belt})
    df1 = pd.concat([df1,df2])

fighter_ids = []

column_length = len(df1['Fighter ID'])

for x in range(1, column_length+1):
    fighter_ids.append(x)

df1['Fighter ID'] = fighter_ids

df1.to_csv('fighter.csv', index=False, encoding='utf-8')

hrefTemp1 = []

for letter in alphabet:
    html = requests.get('http://ufcstats.com/statistics/fighters?char={}&page=all'.format(letter)).text
    soup = BeautifulSoup(html, 'lxml')
    tr = soup.find_all('a', class_ = 'b-link b-link_style_black')
    hrefTemp2 = [a.get('href') for a in tr]
    hrefTemp1 = hrefTemp1 + hrefTemp2

href = []
count = 0
for link in hrefTemp1:
    if count % 3 == 0:
        href.append(hrefTemp1[count])
    count += 1

fighter_dict = {}
trTemp = []
count = 0
tr = []
tr2 = []

for link in href:
    html = requests.get(link).text
    soup = BeautifulSoup(html, 'lxml')
    trTemp = soup.find_all('a', class_='b-link b-link_style_black')
    nicknames = soup.find_all('p', class_='b-content__Nickname')
    contentTitle = soup.find_all('span', class_='b-content__title-highlight')
    if len(trTemp) !=0:
        for tag in trTemp:
            if count % 3 != 2:
                tr.append(tag.text.strip()+nicknames[0].text.strip())
                tr2.append(tag.text.strip())
                print(tag.text.strip()+nicknames[0].text.strip())
            count += 1
        count = 0
    else:
        tr.append(contentTitle[0].text.strip()+nicknames[0].text.strip())
        tr.append('No known opponents')
        tr2.append(contentTitle[0].text.strip())
        tr2.append('No known opponents')
        print(contentTitle[0].text.strip()+nicknames[0].text.strip())
        print('No known opponents')

        count = 0

count = 0
fighters = []
opponents = []
for fighter in tr:
    if count % 2==0:
        fighters.append(fighter)
    count += 1

count = 0

for fighter in tr2:
    if count %2 !=0:
        opponents.append(fighter)
    count += 1

current_number = 0
id = 0
result = []
for string in fighters:
    if fighters[current_number] == fighters[current_number-1]:
        result.append(id)
    else:
        result.append(id+1)
        id += 1
    current_number +=1

print(fighters)

df3 = pd.DataFrame({'Fighter': result, 'Opponent': opponents})
df3.to_csv('fight.csv', index=False, encoding='utf-8')


'''''
current_fighter = None
name_dict = {}

for i in range(0, len(tr)-1, 2):
    key = tr[i]
    value = tr[i+1]

    if key in name_dict:
        name_dict[key].append(value)
    else:
        name_dict[key] = [value]

print(name_dict)

'''




