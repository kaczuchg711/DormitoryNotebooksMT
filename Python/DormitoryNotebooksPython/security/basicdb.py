from time import sleep

from MySQLdb._exceptions import IntegrityError
from django.contrib.auth.models import Group
from django.shortcuts import redirect

from organizations.models import Organization, Dorm, Associate_with_Dorms
from rental.models.DBmodels.Item import Item
from security.models.DBmodels.User_Associate_with_Dorm import User_Associate_with_Dorm
from security.models.DBmodels.User_Associate_with_Organization import User_Associate_with_Organization
from users.models import CustomUser


class BasicDB:
    def __init__(self):
        self.groups = {}
        self.users = {}
        self.dorms = {}

    def create_organizations(self):
        if not Organization.organization_in_db("PK"):
            Organization(name="Politechnika Krakowska", acronym="PK").save()
        if not Organization.organization_in_db("UJ"):
            Organization(name="Uniwersytet Jagieloński ", acronym="UJ").save()
        if not Organization.organization_in_db("AGH"):
            Organization(name="Akademia Górniczo Hutnicza", acronym="AGH").save()

    def create_dorms(self):

        self.dorms = {
            "PK": ("DS B1 Bydgoska", "DS1 Rumcajs", "DS2 Leon", "DS3 Bartek", "DS4 Balon"),
            "UJ": ("Akademik UJ1", "Akademik UJ2","Piast"),
            "AGH": ("Olimp", "Akropol", "Hajduczek"),
        }

        organizations_acronyms = self.dorms.keys()

        created_dorms = [x.name for x in Dorm.objects.all()]

        for acronym in organizations_acronyms:
            for name in self.dorms[acronym]:
                if name in created_dorms:
                    continue
                dorm = Dorm(name=name)
                dorm.save()

    def associate_dorms_with_organization(self):
        for organization_acronym in self.dorms.keys():
            for dorm_name in self.dorms[organization_acronym]:
                Associate_with_Dorms.associate(dorm_name, organization_acronym)

    def create_users(self):
        try:
            CustomUser.objects.create_superuser("tkacza", "pomidorowa")
            self.users["student1"] = CustomUser.objects.create_user("student1", "pomidorowa")
            self.users["porter1"] = CustomUser.objects.create_user("porter1", "pomidorowa")
            self.users["supervisor1"] = CustomUser.objects.create_user("supervisor1", "pomidorowa")
        except:
            Warning("Users exist")

    def associate_users_with_organization(self):
        User_Associate_with_Organization.associate("student1", "PK")
        User_Associate_with_Organization.associate("porter1", "PK")
        User_Associate_with_Organization.associate("supervisor1", "PK")

    def associate_users_with_dorms(self):
        User_Associate_with_Dorm.associate("student1", "DS B1 Bydgoska")
        User_Associate_with_Dorm.associate("porter1", "DS B1 Bydgoska")
        User_Associate_with_Dorm.associate("supervisor1", "DS B1 Bydgoska")

    def create_groups(self):


        self.groups = {

            "students": None,
            "porters": None,
            "supervisors": None

        }
        group_already_existed = False
        try:
            self.groups["students"] = Group.objects.create(name='students')
        except:
            Warning("Group exist")
            self.groups["students"] = Group.objects.get(name='students')

        try:
            self.groups["porters"] = Group.objects.create(name='porters')
        except:
            Warning("Group exist")
            self.groups["porters"] = Group.objects.get(name='porters')

        try:
            self.groups["supervisors"] = Group.objects.create(name='supervisors')
        except:
            Warning("Group exist")
            self.groups["supervisors"] = Group.objects.get(name='supervisors')

    def add_user_to_group(self):
        self.groups["students"].user_set.add(self.users["student1"])
        self.groups["porters"].user_set.add(self.users["porter1"])
        self.groups["supervisors"].user_set.add(self.users["supervisor1"])

    def create_items(self):
        dorm_id = Dorm.objects.filter(name="DS B1 Bydgoska")[0].id
        Item.objects.create(name="suszarka", isAvailable=True, dorm_id=dorm_id, number=1)
        for i in range(3):
            Item.objects.create(name="odkurzacz", isAvailable=True, dorm_id=dorm_id, number=i)



def create_basic_db(request):
    db = BasicDB()
    c = Command()
    c.handle()
    db.create_organizations()
    db.create_dorms()
    db.associate_dorms_with_organization()
    db.create_users()
    db.associate_users_with_organization()
    db.associate_users_with_dorms()
    db.create_groups()
    db.add_user_to_group()
    db.create_items()
    return redirect("organization")



from django.core.management.base import BaseCommand
from django.apps import apps

class Command(BaseCommand):
    help = 'Empties all tables in the database'

    def handle(self, *args, **options):
        CustomUser.objects.all().delete()
        self.stdout.write(self.style.SUCCESS('Successfully emptied the user_customuser table.'))
        print(apps.get_models())
        modelssss = apps.get_models()

        modelssss.remove(modelssss[10])
        modelssss.remove(modelssss[10])
        for model in modelssss:
            print(model)
            model.objects.all().delete()
        self.stdout.write(self.style.SUCCESS('Successfully emptied all tables.'))

        modelssss = apps.get_models()
        for model in modelssss:
            print(model)
            model.objects.all().delete()